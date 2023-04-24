package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PushPanel extends FieldAction{
    private final Heading PushDirection;

    public PushPanel(Heading pushDirection) {
        this.PushDirection = pushDirection;
    }
    @Override
    public void doAction(GameController gameController, Space space) {
        
    }
}
