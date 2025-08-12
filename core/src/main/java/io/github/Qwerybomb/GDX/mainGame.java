package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class mainGame extends ScreenAdapter implements gameUtils {

    // creates main game elements
    PerspectiveCamera camera;
    Core game;
    ModelBatch batch = null;
    Model orb = null;
    Environment environment;

    // adds in the main core class
    mainGame(Core game) {
        this.game = game;
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

    }

    // clear out the memory to prevent leaks
    @Override
    public void dispose() {
      batch.dispose();
    }

    // instantiation of the camera and whatnot
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

        // set up the enviornment and lighting
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 0.2f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, 1f, -0.8f, -0.8f));
    }
}
