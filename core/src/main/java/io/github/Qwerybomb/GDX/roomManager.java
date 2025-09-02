package io.github.Qwerybomb.GDX;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.ArrayList;

public class roomManager implements gameUtils {

    // enumerator for storage of ModelGroups for each tiled segment
    enum tileType {
        basicFloor(wallTile, floorTile),
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

    // initializer for room
    roomManager(int width, int length) {

        // iterative to fill the coordinates table properly
        for (int i = 1; i <= width; i++) {
            coordinates.add(new ArrayList<ArrayList<Integer>>());

            // iterative for the length
            for (int j = 1; j <= length; j++) {
                coordinates.get(i - 1).add(new ArrayList<Integer>());
                coordinates.get(i - 1).get(j - 1).add(j);
            }
        }
        // set the Width and Length properly
        this.Width = width;
        this.Length = length;
    }

    // coordinate storage for this class (x first y second)
    public ArrayList<ArrayList<ArrayList<Integer>>> coordinates = new ArrayList<>();

    // basic var storage for class instances
    int Width = 0;
    int Length = 0;

    // functions for dealing with coordinates
    public void cordPut(int x, int y, tileType tile) {
       int i = 0;
        while (i < tile.modelGroup.size() - 1) {
            String modelName = "Room: " + rooms.indexOf(this) + "-" + String.valueOf(x) + "," + String.valueOf(y)+ "," + i;
            modelAdd(tile.modelGroup.get(i), modelName);
            coordinates.get(x).get(y).add(models.size());
            i++;
        }
    }
}
