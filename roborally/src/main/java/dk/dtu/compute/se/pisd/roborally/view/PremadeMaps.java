package dk.dtu.compute.se.pisd.roborally.view;

public enum PremadeMaps {
    MAP1(new int[][]{
            {1,1,1,1,1,1,1,1},
            {0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,3,0,0},
            {0,0,3,0,0,0,0,0},
            {0,0,0,1,0,4,5,6},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}});

    public final int[][] mapArray;

    PremadeMaps(int[][] MapArray) {
        mapArray = MapArray;
    }
}
