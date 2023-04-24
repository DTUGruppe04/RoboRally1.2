package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;


public class GreenConveyor extends FieldAction{

    private final Heading Direction;

    public GreenConveyor(Heading direction) {
        this.Direction = direction;
    }
    @Override
    public void doAction(GameController gameController, Space space) {
        movePlayer(gameController, space, this.Direction);
    }
}
