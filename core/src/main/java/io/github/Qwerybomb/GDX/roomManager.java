package io.github.Qwerybomb.GDX;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.ArrayList;

public class roomManager implements gameUtils {

    // enumerator for storage of ModelGroups for each tiled segment
    enum tileType {
        basicFloor,
        stoneWall,
        windowWall;
    public ArrayList<Model> modelGroup;

        tileType() {
            modelGroup = new ArrayList<>();
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
    }

    // coordinate storage for this class (x first y second)
    public ArrayList<ArrayList<ArrayList<Integer>>> coordinates = new ArrayList<>();

    // personal storage for class instances
    int coordinateX = 0;
    int coordinateY = 0;
    int personalModelID = 0;

    // functions for dealing with coordinates
    public void cordPut(int x, int y, tileType tile) {
       int i = 0;
        while (i < tile.modelGroup.size() - 1) {
            String modelName = String.valueOf(coordinateX) + "," + String.valueOf(coordinateY)+ "," + i;
            modelAdd(tile.modelGroup.get(i), modelName);
            coordinates.get(x).get(y);
            i++;
        }
    }
}
