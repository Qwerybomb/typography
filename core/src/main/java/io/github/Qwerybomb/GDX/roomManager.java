package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;

public class roomManager implements gameUtils {

    // coordinate storage for the given room object (x first y second)
    public ArrayList<ArrayList<ArrayList<modelGroup>>> coordinates = new ArrayList<>();

    // basic var storage for class instances
    int Width = 0;
    int Length = 0;
    Vector3 roomCenter = new Vector3();

    // enumerator for storage of ModelGroups for each tiled segment
    enum tileType {
        basicFloor(floorTile),
        stoneWall(),
        windowWall();
        public final ArrayList<Model> modelGroup;

        tileType(Model... models) {
            modelGroup = new ArrayList<>();
            modelGroup.addAll(Arrays.asList(models));
        }
    }

    // initializer for room
    roomManager(int width, int length) {

        // iterative to fill the coordinates table properly
        for (int i = 1; i <= width; i++) {
            coordinates.add(new ArrayList<ArrayList<modelGroup>>());

            // iterative for the length
            for (int j = 1; j <= length; j++) {
                coordinates.get(i - 1).add(new ArrayList<modelGroup>());
            }
        }
        // set the Width and Length properly
        this.Width = width;
        this.Length = length;
    }

    // functions for adding a tile type to a specific coordinate
    public void cordPut(int x, int y, tileType tile) {
        coordinates.get(x).get(y).add(new modelGroup(tile.modelGroup).orient(new Vector3(ftBounds.x * x, 0 , ftBounds.z * y)));
    }

    // function for the room algorithm
    public roomManager roomGenerate() {
        int Rectangles = rand(2, 5);
        for (int i = 0; i < 1; i++) {

            // Ensure rectangles are sufficiently sized
            int RectXMin = rand(0, this.Width - 10);
            int RectXMax = rand(RectXMin + 3, this.Width);
            int RectZMin = rand(0, this.Length - 10);
            int RectZMax = rand(RectZMin + 3, this.Length);

            System.out.println(RectXMin);
            System.out.println(RectXMax);
            System.out.println(RectZMin);
            System.out.println(RectZMax);

            // iterate to make all rectangles
            for (int j = RectZMin; j < RectZMax; j++) {
                for (int k = RectXMin; k < RectXMax; k++) {
                    cordPut(k, j, tileType.basicFloor);
                }
            }
        }

        return this;
    }
}

