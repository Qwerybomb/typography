package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class modelGroup implements gameUtils {

    // storage for all models in the list
    ArrayList<Object> groupedObs = new ArrayList<>();

    // var storing the modelGroup center point
    Vector3 centerPoint = new Vector3(0,0,0);

    // constructors for initial filling of the object with models
    public modelGroup(Object... objects) {
        groupedObs.addAll(List.of(objects));
    }
    public modelGroup(ArrayList<Object> objects) {
        groupedObs.add(objects);
    }

    // function for rotating part around a central axis
    public modelGroup axisRotate(Vector3 axis, float degrees) {

        // set up rotation matrix
        Matrix4 rotationMatrix = new Matrix4().setToRotation(axis, degrees);

        for (Object m : groupedObs) {
            if (m.getClass() == ModelInstance.class) {
                Vector3 position = new Vector3();
                ((ModelInstance) m).transform.getTranslation(position);
                Vector3 offset = position.cpy().sub(centerPoint).rot(rotationMatrix);
                ((ModelInstance) m).transform.setTranslation(centerPoint.cpy().add(offset));
                ((ModelInstance) m).transform.rotate(axis, degrees);
            }
        }
        return this;
    }

    // function for forcibly reorienting all parts
    public modelGroup orient(Vector3 location) {
        Vector3 change = location.cpy().sub(centerPoint);

        for (Object m : groupedObs) {
            if (m.getClass() == ModelInstance.class) {
                ((ModelInstance) m).transform.translate(change);
            }
            if (m.getClass() == ParticleEffect.class) {
                ((ParticleEffect) m).translate(change);
            }
        }

        // update center point
        centerPoint.set(location);
        return this;
    }

    // function for setting a new centerpoint
    public modelGroup recenter(Vector3 axis) {
        centerPoint = axis;
        return this;
    }

    // functions for adding models to the group after init
    public modelGroup add3dObject(Object... objects) {
        groupedObs.addAll(List.of(objects));
        return this;
    }
    public modelGroup add3dObject(ArrayList<Object> objects) {
        groupedObs.add(objects);
        return this;
    }

    // function for merging two modelGroups

    // functions for terminating a modelGroup
    public void terminateInstance() {
        for (Object m : groupedObs) {
            models.remove(m);
        }
    }
}
