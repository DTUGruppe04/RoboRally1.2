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
        Heading heading = this.Direction;
        Space target = gameController.board.getNeighbour(space, heading);
        // Check that the target space is valid, the player is valid and on the board,
        // and the movement is allowed based on the borders of the spaces involved.
        if (target != null && space.getPlayer() != null && space.getPlayer().board == gameController.board && space != null &&
                !space.getType().BorderHeadings.contains(heading) &&
                !target.getType().BorderHeadings.contains(gameController.reverseHeading(heading))) {
            Player targetPlayer = target.getPlayer();
            if (targetPlayer == null) {
                // If the target space is empty, move the player to it.
                target.setPlayer(space.getPlayer());
            } else {
                // If the target space is occupied, try to push the other player
                // to the next space in the same direction if possible.
                Space pushSpace = gameController.board.getNeighbour(target, heading);
                if (pushSpace != null && !target.getType().BorderHeadings.contains(heading) &&
                        !pushSpace.getType().BorderHeadings.contains(gameController.reverseHeading(heading))) {
                    pushSpace.setPlayer(targetPlayer);
                    target.setPlayer(space.getPlayer());
                }
            }
        }
    }
    }
}
