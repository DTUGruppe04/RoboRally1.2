package dk.dtu.compute.se.pisd.roborally.fieldActions;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Laser extends FieldAction {

    private final int LaserAmount;

    private final Heading LaserDirection;

    public Laser(int laserAmount, Heading laserDirection) {
        LaserAmount = laserAmount;
        LaserDirection = laserDirection;
    }

    public void searchTarget(Space space, Heading heading, GameController gameController) {
        if(space.isPlayerOnSpace()) {
            System.out.println("Added " + LaserAmount + " SpamCards");
            space.getPlayer().addSpamCards(LaserAmount);
        } else {
            Space neighbour = space.board.getNeighbour(space, heading);
            while (true) {
                if(neighbour.isPlayerOnSpace()) {
                    System.out.println("Added " + LaserAmount + " SpamCards");
                    neighbour.getPlayer().addSpamCards(LaserAmount);
                    break;
                }
                if(gameController.isWall(neighbour, heading)) {
                    //System.out.println("HIT A WALL");
                    break;
                }
                neighbour = space.board.getNeighbour(neighbour, heading);
            }
        }
    }
    @Override
    public void doAction(GameController gameController, Space space) {
        searchTarget(space, LaserDirection, gameController);
    }
}
