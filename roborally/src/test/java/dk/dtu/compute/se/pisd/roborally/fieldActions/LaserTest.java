package dk.dtu.compute.se.pisd.roborally.fieldActions;

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
public class LaserTest {
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
    void givePlayerOneSpamCard() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(1, 9);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();
        gameController.moveForward(player1);

        assertEquals(1, player1.getSpamCards());
        assertEquals(board.getSpace(1, 11), player1.getSpace());
    }

    @Test
    void givePlayerTwoSpamCards() {
        Player player1 = board.getPlayer(0);

        Player player2 = board.getPlayer(1);
        player2.setHeading(Heading.NORTH);

        Space space = board.getSpace(0,8);

        player1.setSpace(space);
        player1.setHeading(Heading.EAST);

        gameController.moveForward(player1);
        gameController.executeBoardElements();
        gameController.moveForward(player1);

        assertEquals(2, player1.getSpamCards());
        assertEquals(board.getSpace(2, 8), player1.getSpace());
    }

    @Test
    void givePlayerThreeSpamCards() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(2,8);

        player1.setSpace(space);
        player1.setHeading(Heading.EAST);

        gameController.moveForward(player1);
        gameController.executeBoardElements();
        gameController.moveForward(player1);

        assertEquals(3, player1.getSpamCards());
        assertEquals(board.getSpace(4, 8), player1.getSpace());
    }

    @Test
    void playerGoingThroughLaserHead() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(0, 9);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();
        gameController.moveForward(player1);

        assertEquals(1, player1.getSpamCards());
        assertEquals(board.getSpace(0,11), player1.getSpace());
    }
    @Test
    void playerGoingThroughLaserEnd() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(4, 9);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();
        gameController.moveForward(player1);

        assertEquals(1, player1.getSpamCards());
        assertEquals(board.getSpace(4,11), player1.getSpace());
    }

    @Test
    void laserCannotHitPlayerBehindWall() {
        Player player1 = board.getPlayer(0);

        player1.setSpace(board.getSpace(7, 10));
        player1.setHeading(Heading.SOUTH);

        gameController.executeBoardElements();

        assertEquals(0, player1.getSpamCards());

        player1.setSpace(board.getSpace(3,10));

        gameController.executeBoardElements();

        assertEquals(0, player1.getSpamCards());

        player1.setSpace(board.getSpace(8, 10));

        gameController.executeBoardElements();

        assertEquals(0, player1.getSpamCards());
    }
}
