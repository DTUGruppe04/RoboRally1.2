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
            {0,0,0,0,0,0,0,0},
            {11,0,12,0,13,0,14,0},
            {0,0,0,0,0,0,0,0}}, "Test Map"),

    CORRIDOR_BLITZ(new int[][]{
            {0,8,0,0,0,0,0,0,13,0},
            {11,0,0,0,0,20,0,0,0,6},
            {0,0,36,0,0,0,25,34,0,0},
            {0,0,28,13,10,10,10,0,0,0},
            {0,20,0,13,0,19,12,0,0,0},
            {0,1,0,13,0,0,12,0,20,0},
            {0,0,0,11,11,11,12,31,2,0},
            {0,0,35,22,0,0,0,37,0,0},
            {6,0,0,0,20,0,0,0,0,10},
            {0,12,0,0,0,0,0,0,9,0}}, "Corridor Blitz"),

    SPRINT_CRAMP(new int[][]{
            {0,0,12,0,0,0,0,0,13,0},
            {11,11,12,0,7,7,9,0,13,3},
            {0,0,2,7,8,0,9,0,11,11},
            {0,7,7,8,0,0,9,0,0,0},
            {0,8,0,0,0,0,7,7,9,0},
            {0,8,6,6,0,0,0,0,9,0},
            {0,0,0,8,0,0,9,6,6,0},
            {10,10,0,8,0,9,6,0,0,0},
            {0,12,0,8,6,6,0,13,10,10},
            {0,12,0,0,0,0,0,13,1,0},}, "Sprint Cramp");

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
