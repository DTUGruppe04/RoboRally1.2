package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class BlueConveyor extends FieldAction{

    private final Heading Direction;

    public BlueConveyor(Heading direction) {
        this.Direction = direction;
    }
    @Override
    public void doAction(GameController gameController, Space space) {

    }
}
