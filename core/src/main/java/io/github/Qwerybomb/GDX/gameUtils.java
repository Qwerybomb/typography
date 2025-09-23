package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public interface gameUtils {

    // basic variables
    Vector3 ftBounds = new Vector3(10.545552f, 2.369924f, 10.545552f);
    float CameraSpeed = 10f;
    AtomicInteger DeltaX = new AtomicInteger();
    AtomicInteger DeltaY = new AtomicInteger();
    AtomicInteger headBob = new AtomicInteger();
    AtomicInteger uiY = new AtomicInteger(0);

    // storage for all models and assets during runtime
    ArrayList<ModelInstance> models = new ArrayList<>();
    ArrayList<roomManager> rooms = new ArrayList<>();
    ArrayList<Entities> entities = new ArrayList<>();
    ArrayList<Decal> decals = new ArrayList<>();
    ArrayList<ParticleEffect> particles = new ArrayList<>();

    // Load in basic models
    ObjLoader objLoad = new ObjLoader();
    Model orb = objLoad.loadModel(Gdx.files.internal("orb/orb2.obj"), true);
    Model orbBase = objLoad.loadModel(Gdx.files.internal("orbBase/base.obj"), true);
    Model floorTile = objLoad.loadModel(Gdx.files.internal("floorTile/floorTile.obj"), true);
    Model wallTile = objLoad.loadModel(Gdx.files.internal("wallTile/wallTile.obj"), true);

    // 2d references (FitViewport for sizing issues)
    TextureAtlas playButtons = new TextureAtlas("textures/playButtons.atlas");
    TextureAtlas wands = new TextureAtlas("textures/wandViewModels.atlas");

    // functions for adding things to the various lists
    public default Decal decalAdd(Decal decal) {
        decals.add(decal);
        return decal;
    }
    public default ModelInstance modelAdd(ModelInstance model) {
        models.add(model);
        return model;
    }
    public default ParticleEffect particleAdd(ParticleEffect particle) {
        particles.add(particle);
        return particle;
    }


    // function for pausing
    public default void pause(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    // anonymous inputAdapter class for precise mouse control
    InputAdapter inputs = new InputAdapter() {
        int lastMouseX;
        int lastMouseY;
        PerspectiveCamera camera;

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            int deltaX = screenX - lastMouseX;
            int deltaY = screenY - lastMouseY;

            DeltaX.set(deltaX);
            DeltaY.set(deltaY);

            lastMouseX = screenX;
            lastMouseY = screenY;
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            int deltaX = screenX - lastMouseX;
            int deltaY = screenY - lastMouseY;

            DeltaX.set(deltaX);
            DeltaY.set(deltaY);

            lastMouseX = screenX;
            lastMouseY = screenY;
            return true;
        }
    };

    // function for player movement
    public default void playerMovement(PerspectiveCamera camera, float Speed) {
        // makes sure cursor is caught and input processor works
        if (!Gdx.input.isCursorCatched()) {Gdx.input.setCursorCatched(true); Gdx.input.setInputProcessor(inputs); }

        // move the camera with deltas
        float calculatedX = -DeltaX.get() * CameraSpeed * Gdx.graphics.getDeltaTime();
        float calculatedY = -DeltaY.get() * CameraSpeed * Gdx.graphics.getDeltaTime();
        camera.direction.rotate(Vector3.Y, calculatedX);
        Vector3 right = camera.direction.cpy().crs(Vector3.Y).nor();

        // make sure the camera doesn't ground glitch
        if (camera.direction.cpy().rotate(right, calculatedY).y < -0.95f) {
            calculatedY = 0;
        }

        camera.direction.rotate(right, calculatedY);
        camera.direction.nor();
        camera.update();

        // prevents camera drifting
        DeltaX.set(0);
        DeltaY.set(0);

        float angleOffset = 0;
        float movementPressed = 0;

        // use trigonometry to move the player
       if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            movementPressed += 1;
       }
       if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            angleOffset += (float) Math.PI / 2;
            movementPressed += 1;
       }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            angleOffset += (float) -Math.PI / 2;
            movementPressed += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            angleOffset += (float) Math.PI;
            movementPressed += 1;
        }
            angleOffset /= movementPressed;

        // special exception cause idk why this happens
        if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            angleOffset = (float) ((-Math.PI / 2) + -Math.PI) / 2;
        }

        //head bob for UI
        if (movementPressed != 0) {
            headBob.set(headBob.get() + 1);
        }
        float zMovement = (float) (Math.cos(Math.atan2(camera.direction.x, camera.direction.z) + angleOffset) * Speed);
        float xMovement = (float) (Math.sin(Math.atan2(camera.direction.x, camera.direction.z) + angleOffset) * Speed);

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.set(camera.position.x + xMovement, camera.position.y, camera.position.z + zMovement);
        }
       camera.update();
    }

    // function to create a room
    public default void roomCreate(int width, int length) {
        rooms.add(new roomManager(width,length).roomGenerate());
    }

    // function for randomness
    public default int rand(double min, double max) {
        return (int) (Math.random() * ((max + 1) - min) + min);
    }
}
