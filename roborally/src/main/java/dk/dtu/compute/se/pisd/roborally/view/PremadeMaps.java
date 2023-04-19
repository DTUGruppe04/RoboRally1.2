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

public enum PremadeMaps {
    MAP1(new int[][]{
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {1,2,3,4,5,0,0,0},
            {6,0,0,0,0,0,0,0},
            {7,0,8,0,9,0,10,0},
            {0,0,0,0,0,0,0,0},
            {11,0,12,0,13,0,14,0},
            {0,0,0,0,0,0,0,0}});

    public final int[][] mapArray;

    PremadeMaps(int[][] MapArray) {
        mapArray = MapArray;
    }
}
