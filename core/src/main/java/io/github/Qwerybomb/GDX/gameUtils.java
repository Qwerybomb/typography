package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;


public interface gameUtils {
    // storage for all models and basic variable init
    ArrayList<ModelInstance> models = new ArrayList<>();
    HashMap<String, Integer> modelNames = new HashMap<>();

    // Load in basic models
    ObjLoader objLoad = new ObjLoader();
    Model orb = objLoad.loadModel(Gdx.files.internal("orb/orb2.obj"), true);
    Model orbBase = objLoad.loadModel(Gdx.files.internal("orbBase/base.obj"), true);
    Model floorTile = objLoad.loadModel(Gdx.files.internal("floorTile/floorTile.obj"), true);
    // 2d handling
    Stage uiStage = new Stage();
    TextureAtlas playButtons = new TextureAtlas("textures/playButtons.atlas");

    // functions to simplify adding and retrieving models from a list
    public default ModelInstance modelGet(String name) {
        return models.get(modelNames.get(name));
    }
    public default ModelInstance modelAdd(Model mod, String name) { models.add(new ModelInstance(mod)); modelNames.put(name, models.size() - 1); return modelGet(name); }

    // function for pausing
    public default void pause(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    // function for creating the spawn cathedral
    public default void makeCathedral(int x, int y, int z) {
        modelAdd(floorTile, "Cathedral_1").transform.setToTranslation(x, y, z);
        modelAdd(floorTile, "Cathedral_2").transform.setToTranslation(x + 10.898854f, y, z);
    }

    // loop function for player movement
    public default void playerMovement(PerspectiveCamera camera) {
       if (!Gdx.input.isCursorCatched()) Gdx.input.setCursorCatched(true);
       if (Gdx.input.isKeyPressed(Input.Keys.W)) {
           camera.position.set(camera.position.x + 1, camera.position.y, camera.position.z);
       }
       camera.update();
    }
}
