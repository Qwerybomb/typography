package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class mainGame implements Screen {

    // creates main game elements
    PerspectiveCamera camera;
    Core game;
    ModelBatch batch = null;
    Model floor = null;
    Environment environment;

    // storage for all models
    ArrayList<ModelInstance> models = new ArrayList<>();
    HashMap<String, Integer> modelNames = new HashMap<>();

    // adds in the main core class
    mainGame(Core game) {
        this.game = game;
    }


    @Override
    public void show() {
        //creation
        Material grass = new Material(TextureAttribute.createDiffuse(new Texture("leaves.png")));
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
        floor = modelBuilder.createBox(20f, 5f, 20f, grass,  VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

        for (int i = 0; i < 1; i++) {
            modelAdd(new ModelInstance(floor),"block_" + Integer.toString(i));
            models.get(models.size() - 1).transform.setToTranslation(0, 0, (i * 5));
        }

        // set up the enviornment and lighting
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 0.2f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, 1f, -0.8f, -0.8f));
    }

    // primary render loop and main code setup
    @Override
    public void render(float v) {

        // clear the screen and begin the batch rendering
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // render all models
        batch.begin(camera);
        for (ModelInstance mod : models) {
            batch.render(mod, environment);
        }

        batch.end();
        modelGet("block_0").transform.rotate(1, 0, 1, 1f);
        System.out.println(Arrays.toString(modelGet("block_0").nodes.toArray()));
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

    // functions to simplify adding and retrieving models from a list
    public ModelInstance modelGet(String name) {
        return models.get(modelNames.get(name));
    }
    public void modelAdd(ModelInstance mod, String name) {
        models.add(mod);
        modelNames.put(name, models.size() - 1);
    }
}
