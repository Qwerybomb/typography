package io.github.Qwerybomb.GDX;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.ArrayList;

public class Entities implements gameUtils {

    // enum for entity types
     enum entityType {
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
    static ArrayList<Integer> entityModelIDs = new ArrayList<>();

    // create an entity
    public static Integer createEntity(entityType entity) {
        return 0;
    }
}
