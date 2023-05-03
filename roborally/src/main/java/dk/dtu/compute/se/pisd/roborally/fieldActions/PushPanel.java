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
        int step = gameController.board.getStep();
        if (step == 1 || step == 3) {                                   //pushes in register 2 and 4
            movePlayer(gameController, space, this.PushDirection);
        }
    }
}
