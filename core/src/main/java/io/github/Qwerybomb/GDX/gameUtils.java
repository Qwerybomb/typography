package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


public interface gameUtils {

    // enumerators
    enum equips {
        FLESH,
        EMPRESS;
    }
    enum uiState {
        VIEW,
        SPELLS,
        INVENTORY;
    }

    // basic variables
    Vector3 ftBounds = new Vector3();
    Vector3 wallTileBounds = new Vector3();
    float CameraSpeed = 10f;
    AtomicInteger DeltaX = new AtomicInteger();
    AtomicInteger DeltaY = new AtomicInteger();
    AtomicInteger headBob = new AtomicInteger();
    AtomicInteger uiY = new AtomicInteger(0);
    equips equippedItem = equips.FLESH;
    uiState state = uiState.VIEW;

    // storage for all models and assets during runtime
    ArrayList<ModelInstance> models = new ArrayList<>();
    HashMap<String, Integer> modelNames = new HashMap<>();
    ArrayList<roomManager> rooms = new ArrayList<>();
    ArrayList<Entities> entities = new ArrayList<>();

    // Load in basic models
    ObjLoader objLoad = new ObjLoader();
    Model orb = objLoad.loadModel(Gdx.files.internal("orb/orb2.obj"), true);
    Model orbBase = objLoad.loadModel(Gdx.files.internal("orbBase/base.obj"), true);
    Model floorTile = objLoad.loadModel(Gdx.files.internal("floorTile/floorTile.obj"), true);
    Model wallTile = objLoad.loadModel(Gdx.files.internal("wallTile/wallTile.obj"), true);

    // 2d handling (FitViewport for sizing issues)
    Stage uiStage = new Stage(new FitViewport(640, 360));
    TextureAtlas playButtons = new TextureAtlas("textures/playButtons.atlas");
    TextureAtlas wands = new TextureAtlas("textures/wandViewModels.atlas");
     Image wand = new Image(wands.findRegion("fleshWand"));

    // functions to simplify adding and retrieving models from a list
    public default ModelInstance modelGet(String name) {
      if (!(modelNames.get(name) == null)) {
          return models.get(modelNames.get(name));
      }
          System.out.print(" incorrect naming type: ");
          System.out.println(name);
          return new ModelInstance(new Model());
    }
    public default ModelInstance modelGet(Integer ID) {
            return models.get(ID);
    }
    public default ModelInstance modelAdd(Model mod, String name) { models.add(new ModelInstance(mod)); modelNames.put(name, models.size() - 1); return modelGet(name); }

    // function for pausing
    public default void pause(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    // function for creating the spawn cathedral
    public default void makeCathedral(int x, int y, int z) {
        // calc for Wall
        BoundingBox boundsF = new BoundingBox();
        wallTile.calculateBoundingBox(boundsF);
        boundsF.getDimensions(wallTileBounds);

        // calc for floor
        BoundingBox boundsW = new BoundingBox();
        floorTile.calculateBoundingBox(boundsW);
        boundsW.getDimensions(ftBounds);

        for (int i = 0; i < 5; i++) {
            modelAdd(floorTile, "CathedralFloor_" + i).transform.setToTranslation(x - (ftBounds.x * i), y, z);
            modelAdd(floorTile, "CathedralFloor_" + i + 5).transform.setToTranslation(x - (ftBounds.x * i), y, z + ftBounds.z);
            modelAdd(wallTile, "CathedralWall_" + i).transform.setToTranslation(x - (ftBounds.x * i), y, (float) (z + (ftBounds.z) + 3.9546));
            modelAdd(floorTile, "CathedralFloor_" + i + 10).transform.setToTranslation(x - (ftBounds.x * i), y, z - ftBounds.z);
            modelAdd(wallTile, "CathedralWall_" + i).transform.setToTranslation(x - (ftBounds.x * i), y, (float) (z - (ftBounds.z ) - 3.9546));

        }
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

    // function for handling the view model shown by default
    public default void viewModelHandle(equips wandState) {
        if (wandState == equips.FLESH) {
            wand.setDrawable(new TextureRegionDrawable(wands.findRegion("fleshWand")));
            wand.setPosition(250, (float) (-350 + uiY.get() + (Math.cos(Math.round((float) headBob.get() / 6)) * 7)));
        } else {
            wand.setDrawable(new TextureRegionDrawable(wands.findRegion("empressWand")));
            wand.setPosition(250, (float) (-390 + uiY.get() + (Math.cos(Math.round((float) headBob.get() / 6)) * 7)));
        }
        wand.setOrigin(Align.center);
        wand.setScale(0.4f);
    }

    // function to create a room
    modelGroup mg = new modelGroup(floorTile, wallTile);
    public default void roomCreate(int width, int length) {
//        rooms.add(new roomManager(width,length));
        mg.groupedModels.get(1).transform.setToTranslation(10,0,10);
    }
}
