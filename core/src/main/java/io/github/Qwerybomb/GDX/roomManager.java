package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;

public class roomManager implements gameUtils {

    // coordinate storage for the given room object (x first y second)
    public ArrayList<ArrayList<ArrayList<modelGroup>>> coordinates = new ArrayList<>();

    // basic var storage for class instances
    int Width = 0;
    int Length = 0;
    Vector3 roomCenter = new Vector3(0,0,0);

    // enumerator for storage of ModelGroups for each tiled segment
    enum tileType {
        basicFloor(floorTile),
        stoneWall(),
        windowWall();

        public final ArrayList<Object> ObjGroup;

        tileType(Object... objects) {
            ObjGroup = new ArrayList<>();
            ObjGroup.addAll(Arrays.asList(objects));

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
        coordinates.get(x).get(y).clear();
        coordinates.get(x).get(y).add(new modelGroup(tile.ObjGroup).orient(roomCenter.cpy().add(new Vector3(ftBounds.x * x, 0 , ftBounds.z * y))));
    }

    // function for the room algorithm
    public roomManager roomGenerate() {
        int Rectangles = 1;
        for (int i = 0; i < Rectangles; i++) {

            // Ensure rectangles are sufficiently sized
            int RectXMin = rand(0, this.Width - 2);
            int RectXMax = rand(RectXMin + 2, this.Width);
            int RectZMin = rand(0, this.Length - 2);
            int RectZMax = rand(RectZMin + 2, this.Length);

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

