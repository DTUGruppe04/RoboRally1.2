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
public class BlueConveyorTest {
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

    //A13.1
    @Test
    void pushPlayerToRightTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(4, 6);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(6,6));
        assertEquals(board.getSpace(6,6).getPlayer(), player1);
    }

    //A13.1
    @Test
    void pushPlayerToLeftTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(2, 6);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(0,6));
        assertEquals(board.getSpace(0,6).getPlayer(), player1);
    }

    //A13.1
    @Test
    void pushPlayerToDownTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(0, 6);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(0,8));
        assertEquals(board.getSpace(0,8).getPlayer(), player1);
    }

    //A13.1
    @Test
    void pushPlayerToUpTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(6, 4);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(6,2));
        assertEquals(board.getSpace(6,2).getPlayer(), player1);
    }

    //A13.2
    @Test
    void cannotPushPlayerThroughAWallTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(7, 2);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), space);
        assertEquals(space.getPlayer(), player1);
    }

    //A13.3
    @Test
    void pushPlayerIntoPitTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(10, 11);

        player1.setSpace(space);
        assertEquals(space.getPlayer(), player1);

        gameController.executeBoardElements();

        assertEquals(player1.getSpace(), board.getSpace(8, 11));
        assertEquals(board.getSpace(8, 11).getPlayer(), player1);
        assertEquals(player1.getSpace().getType(), SpaceType.PIT);
    }
}
