package io.github.Qwerybomb.GDX;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.ArrayList;

public class Entities implements gameUtils {

    // enum for entity types
     enum entityType {
         PLAYER(),
         MASKEATER();

         public final ArrayList<Model> modelGroup;

         entityType(Model... models) {
             modelGroup = new ArrayList<>();
             for (Model m : models) {
                 modelGroup.add(m);
             }
         }
     }

    // entity ID's for this class
    modelGroup entity;

    // entity type per object
    entityType type;

    // create an entity
    public Entities createEntity(entityType entity) {
        this.entity = new modelGroup(entity.modelGroup);
        this.type = entity;
        return this;
    }

    // anonymous Thread for the entities class
    static Thread Logic = new Thread(new Runnable() {
        @Override
        public void run() {

            // uses the entities list from the gameUtils
           for (Entities e: entities) {

           }
        }
    });
}
