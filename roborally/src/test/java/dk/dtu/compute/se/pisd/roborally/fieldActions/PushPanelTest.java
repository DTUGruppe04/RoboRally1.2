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
public class PushPanelTest {
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
    void pushPlayerToRightTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(9, 0);

        //Setting the player on the push panel and confirming that the player is standing on the push panel
        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        board.setPhase(Phase.ACTIVATION);

        //Executing NextStep 3 times
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        //Executing NextStep 4 times (steps = 2)
        gameController.executeStep();

        //Player got pushed to the right by the push panel on step 2
        assertEquals(board.getStep(), 2);
        assertEquals(player1.getSpace(), board.getSpace(10, 0));

        //Setting the player back to the push panel and confirming
        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        //Executing NextStep 3 times again
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        //Executing NextStep one more time (steps = 4)
        gameController.executeStep();

        //Player got pushed to the right by the push panel on step 4
        assertEquals(board.getStep(), 4);
        assertEquals(player1.getSpace(), board.getSpace(10, 0));
    }

    @Test
    void pushPlayerToLeftTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(11, 0);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        board.setPhase(Phase.ACTIVATION);

        //Executing NextStep 3 times
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        //Executing NextStep 4 times (steps = 2)
        gameController.executeStep();

        //Player got pushed to the right by the push panel on step 2
        assertEquals(board.getStep(), 2);
        assertEquals(player1.getSpace(), board.getSpace(10, 0));

        //Setting the player back to the push panel and confirming
        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        //Executing NextStep 3 times again
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        //Executing NextStep one more time (steps = 4)
        gameController.executeStep();

        //Player got pushed to the right by the push panel on step 4
        assertEquals(board.getStep(), 4);
        assertEquals(player1.getSpace(), board.getSpace(10, 0));
    }

    @Test
    void pushPlayerDownTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(11, 2);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        board.setPhase(Phase.ACTIVATION);

        //Executing NextStep 3 times
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        //Executing NextStep 4 times (steps = 2)
        gameController.executeStep();

        //Player got pushed to the right by the push panel on step 2
        assertEquals(board.getStep(), 2);
        assertEquals(player1.getSpace(), board.getSpace(11, 3));

        //Setting the player back to the push panel and confirming
        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        //Executing NextStep 3 times again
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        //Executing NextStep one more time (steps = 4)
        gameController.executeStep();

        //Player got pushed to the right by the push panel on step 4
        assertEquals(board.getStep(), 4);
        assertEquals(player1.getSpace(), board.getSpace(11, 3));
    }

    @Test
    void pushPlayerUpTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(11, 4);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        board.setPhase(Phase.ACTIVATION);

        //Executing NextStep 3 times
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        //Executing NextStep 4 times (steps = 2)
        gameController.executeStep();

        //Player got pushed to the right by the push panel on step 2
        assertEquals(board.getStep(), 2);
        assertEquals(player1.getSpace(), board.getSpace(11, 3));

        //Setting the player back to the push panel and confirming
        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        //Executing NextStep 3 times again
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        //Executing NextStep one more time (steps = 4)
        gameController.executeStep();

        //Player got pushed to the right by the push panel on step 4
        assertEquals(board.getStep(), 4);
        assertEquals(player1.getSpace(), board.getSpace(11, 3));
    }

    @Test
    void cannotPushPlayerThroughAWallTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(7, 0);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        board.setPhase(Phase.ACTIVATION);

        //Executing NextStep 3 times
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        gameController.executeStep();

        assertEquals(board.getStep(), 2);
        assertEquals(player1.getSpace(), space);
    }

    @Test
    void pushPlayerIntoLaserTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(2, 11);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        board.setPhase(Phase.ACTIVATION);

        //Executing NextStep 3 times
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        gameController.executeStep();

        assertEquals(board.getStep(), 2);
        assertEquals(player1.getSpace(), board.getSpace(2, 10));
        assertEquals(player1.getSpace().getType(), SpaceType.BORDER_CORNER_RIGHT_ONE_EMPTY_LASER_RIGHT);
    }

    @Test
    void pushPlayerIntoPitTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(9, 3);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        board.setPhase(Phase.ACTIVATION);

        //Executing NextStep 3 times
        for (int i = 0; i < 3; i++) {
            gameController.executeStep();
            assertEquals(player1.getSpace(), space);
        }

        gameController.executeStep();
        assertEquals(board.getStep(), 2);
        assertEquals(player1.getSpace(), board.getSpace(9, 2));
        assertEquals(player1.getSpace().getType(), SpaceType.PIT);
    }

}
