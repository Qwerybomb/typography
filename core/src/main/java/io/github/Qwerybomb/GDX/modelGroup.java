package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class modelGroup implements gameUtils {

    // storage for all models in the list
    ArrayList<ModelInstance> groupedModels = new ArrayList<>();

    // var storing the modelGroup center point
    Vector3 centerPoint = new Vector3(0,0,0);

    // constructors for initial filling of the object with models
    public modelGroup(Model... mod) {
        for (Model m: mod) {
            groupedModels.add(modelAdd(m, "placeholder"));
        }
    }
    public modelGroup(ArrayList<Model> mod) {
        for (Model m: mod) {
            groupedModels.add(modelAdd(m, "placeholder"));
        }
    }

    // function for rotating part around a central axis
    public void axisRotate(Vector3 axis, float degrees) {

        // set up rotation matrix
        Matrix4 rotationMatrix = new Matrix4().setToRotation(axis, degrees);

        for (ModelInstance m : groupedModels) {
            Vector3 position = new Vector3();
            m.transform.getTranslation(position);
            Vector3 offset = position.cpy().sub(centerPoint).rot(rotationMatrix);
            m.transform.setTranslation(centerPoint.cpy().add(offset));
            m.transform.rotate(axis, degrees);
        }
    }

    // function for setting a new centerpoint
    public void recenter(Vector3 axis) {
        centerPoint = axis;
    }

    // functions for adding models to the group after init
    public void addModel(ModelInstance... mod) {
        for (ModelInstance m : mod) {
            groupedModels.add(m);
        }
    }
    public void addModel(Model... mod) {
        for (Model m : mod) {
            groupedModels.add(modelAdd(m, "placeholder"));
        }
    }
}
