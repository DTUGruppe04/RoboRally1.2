package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Laser extends FieldAction{

    private final int LaserAmount;

    public Laser(int laserAmount) {
        LaserAmount = laserAmount;
    }

    @Override
    public void doAction(GameController gameController, Space space) {

    }
}
