package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public abstract class FieldAction {
    public abstract void doAction(GameController gameController, Space space);

    static void movePlayer(GameController gameController, Space space, Heading direction) {
        Space target = gameController.board.getNeighbour(space, direction);
        // Check that the target space is valid, the player is valid and on the board,
        // and the movement is allowed based on the borders of the spaces involved.
        if (target != null && space.getPlayer() != null && space.getPlayer().board == gameController.board &&
                !space.getType().BorderHeadings.contains(direction) &&
                !target.getType().BorderHeadings.contains(gameController.reverseHeading(direction))) {
            Player targetPlayer = target.getPlayer();
            if (targetPlayer == null) {
                // If the target space is empty, move the player to it.
                target.setPlayer(space.getPlayer());
            } else {
                // If the target space is occupied, try to push the other player
                // to the next space in the same direction if possible.
                Space pushSpace = gameController.board.getNeighbour(target, direction);
                if (pushSpace != null && !target.getType().BorderHeadings.contains(direction) &&
                        !pushSpace.getType().BorderHeadings.contains(gameController.reverseHeading(direction))) {
                    pushSpace.setPlayer(targetPlayer);
                    target.setPlayer(space.getPlayer());
                }
            }
        }
    }
}
