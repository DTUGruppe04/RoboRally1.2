package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.List;

public class Laser extends FieldAction{

    private final int LaserAmount;

    public Laser(int laserAmount) {
        LaserAmount = laserAmount;
    }

    public void searchTarget(Space space, Heading heading) {
        boolean keepSearch = true;
        Space neighbour = space.board.getNeighbour(space, heading);
        while (keepSearch) {
            System.out.println("Inside While Loop");
            if(neighbour.isPlayerOnSpace()) {
                System.out.println("Added " + LaserAmount + " SpamCards");
                neighbour.getPlayer().addSpamCards(LaserAmount);
                keepSearch = false;
            }
            if(neighbour.getType().BorderHeadings.contains(Heading.SOUTH)) {
                keepSearch = false;
            }
            neighbour = space.board.getNeighbour(neighbour, heading);
        }
    }

    @Override
    public void doAction(GameController gameController, Space space) {
        //List<Heading> LaserHeading = space.getType().BorderHeadings;

        Heading heading = gameController.reverseHeading(space.getType().BorderHeadings.get(0));
        searchTarget(space, heading);

        /*
        switch (space.getType().BorderHeadings.get(0)) {
            case NORTH:
                Heading heading = Heading.SOUTH;
                break;
            case EAST:
                Heading heading = Heading.WEST;
                break;
            case SOUTH:
                Heading heading = Heading.NORTH;
                break;
            case WEST:
                Heading heading = Heading.EAST;
                break;
            default:
                System.out.println("Laser not working.");
        }
         */
    }
}
