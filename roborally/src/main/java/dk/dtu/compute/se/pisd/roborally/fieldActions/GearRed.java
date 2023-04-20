package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class GearRed extends FieldAction{
    @Override
    public void doAction(GameController gameController, Space space) {
        gameController.turnRight(space.getPlayer());
    }
}
