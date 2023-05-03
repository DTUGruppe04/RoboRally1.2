package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

import dk.dtu.compute.se.pisd.roborally.view.PremadeMaps;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
public class PlayerTest {
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");
    private GameController gameController;
    private Board board;

    @BeforeEach
    void setUp() {
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
    void setPlayerOnSpaceTest() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(10, 1);

        player1.setSpace(space);

        assertEquals(space.getPlayer(), player1);
        assertEquals(player1.getSpace(), space);
    }

    @Test
    void addSpamCardsToPlayerTest() {
        Player player1 = board.getPlayer(0);
        player1.addSpamCards(4);

        gameController.startProgrammingPhase();

        assertTrue(player1.getSpamCards() == 4);
        for (int i = 0; i < 4; i++) {
            assertEquals("Spam Card", player1.getCardField(i).getCard().getName());
        }
        for (int i = 4; i < 9; i++) {
            assertNotEquals("Spam Card", player1.getCardField(i).getCard().getName());
        }
    }

    @Test
    void cannotAddMoreThan9SpamCardsToAPlayerTest() {
        Player player1 = board.getPlayer(0);
        player1.addSpamCards(15);

        gameController.startProgrammingPhase();

        assertTrue(player1.getSpamCards() == 9);

        for (int i = 0; i < 9; i++) {
            assertEquals("Spam Card", player1.getCardField(i).getCard().getName());
        }
    }

    @Test
    void decreaseSpamCardsForPlayerTest() {
        Player player1 = board.getPlayer(0);
        player1.addSpamCards(15);

        assertTrue(player1.getSpamCards() == 9);

        player1.decSpamCards();

        assertTrue(player1.getSpamCards() == 8);

    }

}
