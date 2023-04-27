package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.List;

public class Laser extends FieldAction{

    private final int LaserAmount;

    private final Heading LaserDirection;

    public Laser(int laserAmount, Heading laserDirection) {
        LaserAmount = laserAmount;
        LaserDirection = laserDirection;
    }

    public boolean isWall(Space space) {
        return space.getType().BorderHeadings.contains(Heading.SOUTH) ||
                space.getType().BorderHeadings.contains(Heading.NORTH) ||
                space.getType().BorderHeadings.contains(Heading.WEST) ||
                space.getType().BorderHeadings.contains(Heading.EAST);
    }

    public void searchTarget(Space space, Heading heading) {
        if(space.isPlayerOnSpace()) {
            System.out.println("Added " + LaserAmount + " SpamCards");
            space.getPlayer().addSpamCards(LaserAmount);
        } else {
            Space neighbour = space.board.getNeighbour(space, heading);
            while (true) {
               // neighbour.getPosition();
               // System.out.println("Player: " + neighbour.getPlayer());
               // System.out.println("");
                if(neighbour.isPlayerOnSpace()) {
                    System.out.println("Added " + LaserAmount + " SpamCards");
                    neighbour.getPlayer().addSpamCards(LaserAmount);
                    break;
                }
                if(isWall(neighbour)) {
                    System.out.println("He hit a wall");
                    break;
                }
                neighbour = space.board.getNeighbour(neighbour, heading);
            }
        }
    }
    @Override
    public void doAction(GameController gameController, Space space) {
        searchTarget(space, LaserDirection);
    }
}
