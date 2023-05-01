package dk.dtu.compute.se.pisd.roborally.view;

/*
* 0 = EMPTY_SPACE
* 1 = CHECKPOINT
* 2 = PIT
* 3 = BORDER_LEFT
* 4 = BORDER_RIGHT
* 5 = BORDER_UP
* 6 = BORDER_DOWN
* 7 = BORDER_CORNER_TOP_LEFT
* 8 = BORDER_CORNER_TOP_RIGHT
* 9 = BORDER_CORNER_BOTTOM_LEFT
* 10 = BORDER_CORNER_BOTTOM_RIGHT
*/

import dk.dtu.compute.se.pisd.roborally.model.SpaceType;

public enum PremadeMaps {
    MAP1(new int[][]{
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {1,2,3,4,5,0,0,0},
            {6,0,0,0,0,0,0,0},
            {7,0,8,0,9,0,10,0},
            {0,0,0,0,14,15,16,17},
            {11,0,12,0,13,0,14,0},
            {0,0,0,0,0,0,0,0}}, "Test Map"),

    CORRIDOR_BLITZ(new int[][]{
            {0,35,36,36,36,36,36,36,36,36,36,39},
            {21,35,37,37,37,37,37,37,37,37,37,41},
            {36,0,0,11,0,0,0,0,0,0,6,0},
            {21,41,8,0,0,0,20,1,0,0,0,12},
            {0,0,0,0,36,28,0,0,0,35,0,0},
            {21,35,0,0,0,13,13,13,11,22,0,0},
            {21,35,0,0,0,10,0,0,11,0,20,0},
            {0,0,0,20,0,10,19,0,11,0,0,0},
            {21,39,0,0,25,10,12,12,12,0,0,0},
            {37,0,0,0,34,0,0,0,31,37,0,0},
            {21,35,13,0,0,0,0,20,2,0,0,9},
            {0,35,0,6,0,0,0,0,0,0,10,0}}, "Corridor Blitz"),

    SPRINT_CRAMP(new int[][]{
            {0,35,36,36,36,36,36,36,36,36,36,39},
            {21,35,37,37,37,37,37,37,37,37,37,41},
            {36,0,0,11,0,0,0,0,0,10,0,0},
            {21,41,0,11,0,7,8,8,0,10,12,12},
            {0,0,12,12,2,7,0,6,0,0,0,0},
            {21,35,0,0,7,8,0,6,8,8,8,0},
            {21,35,0,7,8,0,0,0,0,0,6,0},
            {0,0,0,7,0,0,0,0,0,9,6,0},
            {21,39,0,9,9,9,7,0,9,6,0,0},
            {37,0,0,0,0,0,7,0,6,0,13,13},
            {21,35,13,13,11,0,9,9,6,0,10,1},
            {0,35,0,3,11,0,0,0,0,0,10,0}},"Sprint Cramp");

    public final int[][] mapArray;
    public final String mapName;
    private static final PremadeMaps[] values = values();

    public static PremadeMaps get(int ordinal) { return values[ordinal]; }

    public static PremadeMaps get(String mapName) {
        for (PremadeMaps map : values()) {
            if (map.mapName.equals(mapName)) {
                return map;
            }
        }
        return MAP1;
    }

    PremadeMaps(int[][] MapArray, String MapName) {
        mapArray = MapArray;
        mapName = MapName;
    }
}
