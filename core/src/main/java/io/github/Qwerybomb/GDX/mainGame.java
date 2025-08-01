package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class mainGame implements Screen {

    // creates main game elements
    PerspectiveCamera camera;
    Core game;
    ModelBatch batch = null;
    Model floor = null;
    ModelInstance mod = null;
    Environment environment;
    int i = 0;

    // storage for all models
    ArrayList<ModelInstance> models = new ArrayList<>();

    // adds in the main core class
    mainGame(Core game) {
        this.game = game;
    }


    @Override
    public void show() {
        //creation
        batch = new ModelBatch();
        camera = new PerspectiveCamera(90f,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0f, 0f, 0f);

        // camera render distances
        camera.near = 0.1f;
        camera.far = 300f;
        camera.update();

        // create test cube
        ModelBuilder modelBuilder = new ModelBuilder();
        floor = modelBuilder.createBox(20f, 5f, 20f,
            new Material(ColorAttribute.createDiffuse(1, 0, 0, 1)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        mod = new ModelInstance(floor);

        for (int i = 0; i < 1; i++) {
            models.add(new ModelInstance(floor));
            models.get(models.size() - 1).transform.setToTranslation(0, 0, (i * 5) - 5);
        }

        // set up the enviornment and lighting
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
    }

    // primary render loop and main code setup
    @Override
    public void render(float v) {

        // clear the screen and begin the batch rendering
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin(camera);

        // render all models
        for (ModelInstance mod : models) {
            batch.render(mod, environment);
        }

        System.out.println(Gdx.graphics.getFramesPerSecond());
        batch.end();
        models.get(0).transform.rotate(0, 0, 0, 10);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
      batch.dispose();
      floor.dispose();
    }
}
