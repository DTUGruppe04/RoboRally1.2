package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Pit extends FieldAction {
    @Override
    public void doAction(GameController gameController, Space space) {
        space.getPlayer().setIsInPit(true);
        space.getPlayer().decSpamCards();
        space.getPlayer().decSpamCards();

    }
}
