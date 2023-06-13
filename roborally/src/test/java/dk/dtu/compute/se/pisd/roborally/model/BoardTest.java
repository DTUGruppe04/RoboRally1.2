package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.PremadeMaps;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class BoardTest {
    private GameController gameController;
    private Board board;
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    @BeforeEach
    void setUp() {
        //Choosing our test map
        PremadeMaps testMap = PremadeMaps.get("Test Map");
        board = new Board(testMap.mapArray, testMap.mapName);
        gameController = new GameController(board);

        //Creating 2 players and add them to the board
        for(int i = 0; i < 2; i++) {
            Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
            board.addPlayer(player);
        }
        //Spawning the players in the game
        board.setCurrentPlayer(board.getPlayer(0));
        gameController.spawnPlayers();
    }

    @AfterEach
    void tearDown() {
        gameController = null;
        board = null;
    }

    @Test
    void getSpaceTest() {
        Player player1 = board.getPlayer(0);

        assertEquals(player1.getSpace(), board.getSpace(0,0));
    }

    @Test
    void isSpaceTypeLaserTest() {
        Space space = board.getSpace(8, 7);
        assertTrue(board.isSpaceTypeLaser(space.getType()));

        space = board.getSpace(0, 1);
        assertFalse(board.isSpaceTypeLaser(space.getType()));

        space = board.getSpace(0, 10);
        assertTrue(board.isSpaceTypeLaser(space.getType()));

        space = board.getSpace(2, 11);
        assertFalse(board.isSpaceTypeLaser(space.getType()));
    }

    @Test
    void getNeighbourTest() {
        Space currentSpace = board.getSpace(1, 1);

        assertAll(
                () -> assertEquals(board.getSpace(0,1), board.getNeighbour(currentSpace, Heading.WEST)),
                () -> assertEquals(board.getSpace(2,1), board.getNeighbour(currentSpace, Heading.EAST)),
                () -> assertEquals(board.getSpace(1,0), board.getNeighbour(currentSpace, Heading.NORTH)),
                () -> assertEquals(board.getSpace(1,2), board.getNeighbour(currentSpace, Heading.SOUTH))
        );

    }

    //A12.1
    @Test
    void walkThroughWallsTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(5, 8);

        player1.setSpace(space);
        player1.setHeading(Heading.WEST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughWallsTest2() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(4, 8);

        player1.setSpace(space);
        player1.setHeading(Heading.EAST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }


    //A12.1
    @Test
    void walkThroughWallsTest3() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(9, 7);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughWallsTest4() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(9, 8);

        player1.setSpace(space);
        player1.setHeading(Heading.NORTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughNorthWestWallsTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(8, 4);


        player1.setSpace(space);
        player1.setHeading(Heading.NORTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());


        player1.setSpace(space);
        player1.setHeading(Heading.WEST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughNorthWestWallsTest2() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(8, 3);


        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());

        space = board.getSpace(7, 4);

        player1.setSpace(space);
        player1.setHeading(Heading.EAST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughNorthEastWallsTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(10, 4);


        player1.setSpace(space);
        player1.setHeading(Heading.NORTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());


        player1.setSpace(space);
        player1.setHeading(Heading.EAST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughNorthEastWallsTest2() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(10, 3);


        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());

        space = board.getSpace(11, 4);

        player1.setSpace(space);
        player1.setHeading(Heading.WEST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughSouthEastWallsTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(10, 6);


        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());


        player1.setSpace(space);
        player1.setHeading(Heading.EAST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughSouthEastWallsTest2() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(10, 7);


        player1.setSpace(space);
        player1.setHeading(Heading.NORTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());

        space = board.getSpace(11, 6);

        player1.setSpace(space);
        player1.setHeading(Heading.WEST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughSouthWestWallsTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(8, 6);


        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());


        player1.setSpace(space);
        player1.setHeading(Heading.WEST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }

    //A12.1
    @Test
    void walkThroughSouthWestWallsTest2() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(8, 7);


        player1.setSpace(space);
        player1.setHeading(Heading.NORTH);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());

        space = board.getSpace(7, 6);

        player1.setSpace(space);
        player1.setHeading(Heading.EAST);

        gameController.moveForward(player1);

        assertEquals(space, player1.getSpace());
    }
}
