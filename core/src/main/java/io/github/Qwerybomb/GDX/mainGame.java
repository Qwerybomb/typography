package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.math.Vector3;

public class mainGame extends ScreenAdapter implements gameUtils {

    // creates main game elements
    PerspectiveCamera camera;
    Core game;
    ModelBatch batch = null;
    Environment environment;

    // adds in the main core class
    mainGame(Core game) {
        this.game = game;
    }

    // clear out the memory to prevent leaks
    @Override
    public void dispose() {
      batch.dispose();
      orbBase.dispose();
      floorTile.dispose();
      orb.dispose();
    }

    // instantiation of the camera and whatnot
    @Override
    public void show() {
        //creation
        batch = new ModelBatch();
        camera = new PerspectiveCamera(90f,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());
        camera.position.set(0f, 10f, 0f);

        // camera render distances
        camera.near = 0.1f;
        camera.far = 150f;
        camera.update();

        // set up the enviornment and lighting
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        environment.add(new PointLight().set(new Color(1f, 0.2f, 0.2f, 1f), new Vector3(0,10,0), 30f));

        uiStage.addActor(wand);
        roomCreate(10,10);

    }

    // primary render loop and main code setup
    @Override
    public void render(float v) {

        // clear the screen
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // render all models
        batch.begin(camera);
        for (ModelInstance mod : models) {
            batch.render(mod, environment);
        }
        batch.end();

        // render the 2d elements
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();

        playerMovement(this.camera, 0.2f);

        if (uiState.VIEW == state) {
            viewModelHandle(equippedItem);
        }

    }
    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }
}
