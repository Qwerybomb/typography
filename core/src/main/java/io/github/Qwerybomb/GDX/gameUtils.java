package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import java.util.ArrayList;
import java.util.HashMap;


public interface gameUtils {
    // storage for all models
    ArrayList<ModelInstance> models = new ArrayList<>();
    HashMap<String, Integer> modelNames = new HashMap<>();

    // functions to simplify adding and retrieving models from a list
    public default ModelInstance modelGet(String name) {
        return models.get(modelNames.get(name));
    }
    public default void modelAdd(ModelInstance mod, String name) {
        models.add(mod);
        modelNames.put(name, models.size() - 1);
    }

}
