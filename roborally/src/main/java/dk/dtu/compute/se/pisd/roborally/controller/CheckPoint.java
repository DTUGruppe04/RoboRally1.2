package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;

public class CheckPoint extends FieldAction {

    int checkpointNumber;

    public CheckPoint(int checkpointNumber) {
        this.checkpointNumber = checkpointNumber;
    }

    @Override
    public void doAction(GameController gameController, Space space) {
        if (space.getPlayer().getCheckpoints() == checkpointNumber-1) {
            space.getPlayer().raiseCheckpoints();
            System.out.println(space.getPlayer().getCheckpoints());
        }

    }

}
