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
public class GreenConveyerTest {
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
        Space space = board.getSpace(4, 4);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(5,4));
        assertEquals(board.getSpace(5,4).getPlayer(), player1);
    }

    @Test
    void pushPlayerToLeftTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(2, 4);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(1,4));
        assertEquals(board.getSpace(1,4).getPlayer(), player1);
    }

    @Test
    void pushPlayerToDownTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(0, 4);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(0,5));
        assertEquals(board.getSpace(0,5).getPlayer(), player1);
    }

    @Test
    void pushPlayerToUpTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(6, 6);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(6,5));
        assertEquals(board.getSpace(6,5).getPlayer(), player1);
    }

    @Test
    void cannotPushPlayerThroughAWallTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(6, 2);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), space);
        assertEquals(space.getPlayer(), player1);
    }

    @Test
    void pushPlayerIntoPitTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(7, 11);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(8, 11));
        assertEquals(board.getSpace(8, 11).getPlayer(), player1);
        assertEquals(player1.getSpace().getType(), SpaceType.PIT);
    }

    @Test
    void pushPlayerIntoLaserTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(11, 7);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(10, 7));
        assertEquals(board.getSpace(10, 7).getPlayer(), player1);
        assertEquals(player1.getSpace().getType(), SpaceType.BORDER_CORNER_TOP_ONE_EMPTY_LASER_UP);
    }
}
