package dk.dtu.compute.se.pisd.roborally.controller;

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
class GameControllerTest {
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
    void moveCurrentPlayerToSpaceTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(5, 1);

        gameController.moveCurrentPlayerToSpace(space1);

        assertEquals(player1, space1.getPlayer());
        assertFalse(board.getSpace(0,0).isPlayerOnSpace());
        assertEquals(player2, board.getCurrentPlayer(), "Current player should be " + player2.getName() +"!");
    }

    @Test
    void moveForwardTest() {
        Player player1 = board.getPlayer(0);

        gameController.moveForward(player1);

        assertEquals(player1, board.getSpace(0,1).getPlayer());
        assertEquals(Heading.SOUTH, player1.getHeading());
        assertFalse(board.getSpace(0,0).isPlayerOnSpace());
    }

    //A6.1
    @Test
    void startingProgrammingPhaseTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        gameController.startProgrammingPhase();

        assertEquals(Phase.PROGRAMMING, board.getPhase());
        assertEquals(player1.getCards().length, 9);
        assertEquals(player2.getCards().length, 9);
    }
}