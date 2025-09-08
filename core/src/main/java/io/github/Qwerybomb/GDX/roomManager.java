package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class roomManager implements gameUtils {

    // enumerator for storage of ModelGroups for each tiled segment
    enum tileType {
        basicFloor(floorTile),
        stoneWall(),
        windowWall();
        public final ArrayList<Model> modelGroup;

        tileType(Model... models) {
            modelGroup = new ArrayList<>();
            for (Model m : models) {
                modelGroup.add(m);
            }
        }
    }

    // enum for tile direction
    enum direction {
        FLIP(180),
        CLOCKWISE(-90),
        COUNTERCLOCKWISE(90);

        public final int dir;

        direction(int d) {
            dir = d;
        }
    }

    // initializer for room
    roomManager(int width, int length) {

        // iterative to fill the coordinates table properly
        for (int i = 1; i <= width; i++) {
            coordinates.add(new ArrayList<ArrayList<ModelInstance>>());

            // iterative for the length
            for (int j = 1; j <= length; j++) {
                coordinates.get(i - 1).add(new ArrayList<ModelInstance>());
            }
        }
        // set the Width and Length properly
        this.Width = width;
        this.Length = length;
    }

    // coordinate storage for the given room object (x first y second)
    public ArrayList<ArrayList<ArrayList<ModelInstance>>> coordinates = new ArrayList<>();

    // basic var storage for class instances
    int Width = 0;
    int Length = 0;
    Vector3 roomCenter = new Vector3();

    // functions for dealing with coordinates
    public void cordPut(int x, int y, tileType tile) {

        // adds all objects of a specific tile type to the models list and the coordinate list
        for (int i = 0; i < tile.modelGroup.size(); i++) {
            String modelName = "Room: " + rooms.indexOf(this) + "-" + String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(i);
            modelAdd(tile.modelGroup.get(i), modelName);
            coordinates.get(x).get(y).add(modelGet(modelName));
        }
    }

}

