package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.HashMap;


public interface gameUtils {
    // storage for all models
    ArrayList<ModelInstance> models = new ArrayList<>();
    HashMap<String, Integer> modelNames = new HashMap<>();

    // Load in basic models
    ObjLoader objLoad = new ObjLoader();
    Model orb = objLoad.loadModel(Gdx.files.internal("orb/orb2.obj"), true);
    Model orbBase = objLoad.loadModel(Gdx.files.internal("orbBase/base.obj"), true);

    // 2d handling
    Stage uiStage = new Stage();
    TextureAtlas playButtons = new TextureAtlas("textures/playButtons.atlas");

    // functions to simplify adding and retrieving models from a list
    public default ModelInstance modelGet(String name) {
        return models.get(modelNames.get(name));
    }
    public default void modelAdd(ModelInstance mod, String name) { models.add(mod); modelNames.put(name, models.size() - 1); }

    // function for pausing
    public default void pause(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }
}
