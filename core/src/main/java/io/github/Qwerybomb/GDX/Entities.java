package io.github.Qwerybomb.GDX;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Entities implements gameUtils {

    // enum for entity types
     enum entityType {
         PLAYER(),
         MASKEATER();

        public final ArrayList<Object> ObjGroup;

        entityType(Object... objects) {
            ObjGroup = new ArrayList<>();
            ObjGroup.addAll(Arrays.asList(objects));
        }
     }

    // entity ID's for this class
    modelGroup entity;

    // entity type per object
    entityType type;

    // create the entity
    public Entities(entityType entity) {
        this.entity = new modelGroup(entity.ObjGroup);
        this.type = entity;
    }

    // anonymous Thread for the entities class
    static Thread EntLogic = new Thread(new Runnable() {
        @Override
        public void run() {

            // uses the entities list from the gameUtils
           for (Entities e: entities) {

           }
        }
    });

    // functions for entity tick logic
    private void Orb() {
    }
}
