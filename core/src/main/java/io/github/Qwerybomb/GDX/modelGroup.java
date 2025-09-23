package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class modelGroup implements gameUtils {

    // storage for all 3d elements in the object
    ArrayList<ModelInstance> groupedModels = new ArrayList<>();
    ArrayList<ParticleEffect> groupedParticles = new ArrayList<>();
    ArrayList<Decal> groupedDecals = new ArrayList<>();

    // var storing the modelGroup center point
    Vector3 centerPoint = new Vector3(0,0,0);

    // constructors for initial filling of the object with models
    public modelGroup(Model... mod) {
        for (Model m: mod) {
            groupedModels.add(modelAdd(new ModelInstance(m)));
        }
    }
    public modelGroup(Object... objects) {
        for (Object obj : objects) {
            if (obj.getClass().equals(ModelInstance.class)) {
                groupedModels.add(modelAdd((ModelInstance) obj));
            } else if (obj.getClass().equals(Model.class)) {
                groupedModels.add(modelAdd(new ModelInstance((Model) obj)));
            } else if (obj.getClass().equals(ParticleEffect.class)) {
                groupedParticles.add(particleAdd((ParticleEffect) obj));
            } else if (obj.getClass().equals(Decal.class)) {
                groupedDecals.add(decalAdd((Decal) obj));
            } else {
                System.out.println("invalid modelGroup obj Type" + obj.getClass());
            }
        }
    }
    public modelGroup(ArrayList<Object> objects) {
        for (Object obj : objects) {
            if (obj.getClass().equals(ModelInstance.class)) {
                groupedModels.add(modelAdd((ModelInstance) obj));
            } else if (obj.getClass().equals(Model.class)) {
                groupedModels.add(modelAdd(new ModelInstance((Model) obj)));
            } else if (obj.getClass().equals(ParticleEffect.class)) {
                groupedParticles.add(particleAdd((ParticleEffect) obj));
            } else if (obj.getClass().equals(Decal.class)) {
                groupedDecals.add(decalAdd((Decal) obj));
            } else {
                System.out.println("invalid modelGroup obj Type" + obj.getClass());
            }
        }
    }

    // function for rotating part around a central axis
    public modelGroup axisRotate(Vector3 axis, float degrees) {

        // set up rotation matrix
        Matrix4 rotationMatrix = new Matrix4().setToRotation(axis, degrees);

        for (ModelInstance m : groupedModels) {
            Vector3 position = new Vector3();
            m.transform.getTranslation(position);
            Vector3 offset = position.cpy().sub(centerPoint).rot(rotationMatrix);
            m.transform.setTranslation(centerPoint.cpy().add(offset));
            m.transform.rotate(axis, degrees);
        }
        return this;
    }

    // function for moving the parts
    public modelGroup axisMove(float dir, float units) {

        // temp vectors for all iterations
        Vector3 offset = new Vector3((float) Math.cos(dir) * units,0, (float) Math.sin(dir) * units);
        Vector3 position = new Vector3();

        for (ModelInstance m : groupedModels) {
            m.transform.getTranslation(position);

            // modify coordinates
            position.add(offset);

            m.transform.setTranslation(position);
        }

        centerPoint.add(offset);
        return this;
    }

    // function for forcibly reorienting all parts
    public modelGroup orient(Vector3 location) {
        Vector3 change = location.cpy().sub(centerPoint);

        for (ModelInstance m : groupedModels) {
            m.transform.translate(change);
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
    public modelGroup addModel(ModelInstance... mod) {
        for (ModelInstance m : mod) {
            groupedModels.add(m);
        }
        return this;
    }
    public modelGroup addModel(Model... mod) {
        for (Model m : mod) {
            groupedModels.add(modelAdd(new ModelInstance(m)));
        }
        return this;
    }

    // functions for terminating a modelGroup
    public void terminateInstance() {
        for (ModelInstance m : groupedModels) {
            models.remove(m);
        }
    }
}
