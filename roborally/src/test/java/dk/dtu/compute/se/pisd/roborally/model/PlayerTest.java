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

    @Test
    void player1HittingPlayer2WithLaserTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(0,1);
        Space space2 = board.getSpace(1,1);

        player1.setSpace(space1);
        player1.setHeading(Heading.EAST);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(0, player1.getSpamCards());
        assertEquals(1, player2.getSpamCards());
    }

    @Test
    void player2HittingPlayer1WithLaserTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(0,1);
        Space space2 = board.getSpace(1,1);

        player1.setSpace(space1);
        player1.setHeading(Heading.NORTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.WEST);

        gameController.executeBoardElements();

        assertEquals(1, player1.getSpamCards());
        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void bothPlayersHittingEachOtherTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(0,1);
        Space space2 = board.getSpace(6,1);

        player1.setSpace(space1);
        player1.setHeading(Heading.EAST);

        player2.setSpace(space2);
        player2.setHeading(Heading.WEST);

        gameController.executeBoardElements();

        assertEquals(1, player1.getSpamCards());
        assertEquals(1, player2.getSpamCards());
    }

    @Test
    void player1HittingPlayer28SpacesAwayTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(0,1);
        Space space2 = board.getSpace(0,9);

        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.SOUTH);

        gameController.executeBoardElements();

        assertEquals(1, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPlayer2ThroughAWallEast() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(6,8);
        Space space2 = board.getSpace(7,8);

        player1.setSpace(space1);
        player1.setHeading(Heading.EAST);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());

        player1.setSpace(board.getSpace(5,8));

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPlayer2ThroughAWallWest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(6, 8);
        Space space2 = board.getSpace(4,8);

        player1.setSpace(space1);
        player1.setHeading(Heading.WEST);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());

        player1.setSpace(board.getSpace(5,8));

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPlayer2ThroughAWallNorth() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(9, 8);
        Space space2 = board.getSpace(9, 7);

        player1.setSpace(space1);
        player1.setHeading(Heading.NORTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());

        player1.setSpace(board.getSpace(6, 3));
        player2.setSpace(board.getSpace(6, 1));

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPlayer2ThroughAWallSouth() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(9, 7);
        Space space2 = board.getSpace(9, 8);

        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.SOUTH);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());

        player1.setSpace(board.getSpace(9, 6));

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPLayer2ThroughACorner() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(8,4);
        Space space2 = board.getSpace(8,3);

        player1.setSpace(space1);
        player1.setHeading(Heading.NORTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.SOUTH);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());

        player1.setHeading(Heading.WEST);

        player2.setSpace(board.getSpace(7, 4));

        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void player1CanHitPlayer2OverGreenBeltTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        //Downwards Green Belt
        Space space1 = board.getSpace(0, 3);
        Space space2 = board.getSpace(0, 5);

        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        player2.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.executeBoardElements();

        assertEquals(1, player2.getSpamCards());

        //Sideways Green Belt
        player1.setSpace(board.getSpace(1, 3));
        player2.setSpace(board.getSpace(3,3));

        player1.setHeading(Heading.EAST);

        gameController.executeBoardElements();

        assertEquals(2, player2.getSpamCards());
    }

    @Test
    void player1CanHitPlayer2OverBlueBeltTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        //Downwards Blue Belt
        Space space1 = board.getSpace(0, 5);
        Space space2 = board.getSpace(0, 7);

        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.SOUTH);
        
        gameController.executeBoardElements();
        
        assertEquals(1, player2.getSpamCards());

        //Sideways Blue Belt
        player1.setSpace(board.getSpace(1, 6));
        player2.setSpace(board.getSpace(3,6));

        player1.setHeading(Heading.EAST);

        gameController.executeBoardElements();

        assertEquals(2, player2.getSpamCards());
    }

    @Test
    void player1CanHitPlayer2OverCheckpointTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(0, 0);
        Space space2 = board.getSpace(0, 3);

        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.SOUTH);

        gameController.executeBoardElements();

        assertEquals(1, player2.getSpamCards());

        player1.setSpace(board.getSpace(0, 1));

        gameController.executeBoardElements();

        assertEquals(2, player2.getSpamCards());
    }

    @Test
    void player1CanHitPlayerThroughLasersTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        //Sideways Shooting through lasers
        Space space1 = board.getSpace(7, 8);
        Space space2 = board.getSpace(11, 8);

        player1.setSpace(space1);
        player1.setHeading(Heading.EAST);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(1, player2.getSpamCards());

        //Downwards shooting through lasers
        player1.setSpace(board.getSpace(5, 9));
        player1.setHeading(Heading.SOUTH);

        player2.setSpace(board.getSpace(5,11));
        player2.setHeading(Heading.SOUTH);

        gameController.executeBoardElements();

        assertEquals(2, player2.getSpamCards());
    }

    //This test is failing. Look at the code
    //I think it's because the space have a south wall and also contain a player on the space and we check for walls before players
    //Do we switch this prio we then get a another bug. So we have to look at it
    @Test
    void player1CanHitPlayerThroughPitsTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        //Sideways shooting through pit
        Space space1 = board.getSpace(8,2);
        Space space2 = board.getSpace(10, 2);

        player1.setSpace(space1);
        player1.setHeading(Heading.EAST);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(1, player2.getSpamCards());

        //Shooting downwards through pit
        player1.setSpace(board.getSpace(9, 1));
        player1.setHeading(Heading.SOUTH);

        player2.setSpace(board.getSpace(9, 3));
        player2.setHeading(Heading.SOUTH);

        gameController.executeBoardElements();

        assertEquals(2, player2.getSpamCards());
    }

    @Test
    void player1CanHitPlayerThroughSpawnPointsTest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(0,0);
        Space space2 = board.getSpace(6,0);

        player1.setSpace(space1);
        player1.setHeading(Heading.EAST);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(1, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPlayer2OutsideMapNorth() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(5,0);
        Space space2 = board.getSpace(5,11);

        player1.setSpace(space1);
        player1.setHeading(Heading.NORTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.EAST);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPlayer2OutsideMapSouth() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(5,11);
        Space space2 = board.getSpace(5,0);

        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        player2.setSpace(space2);
        player2.setHeading(Heading.EAST);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPlayer2OutsideMapEast() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(11,1);
        Space space2 = board.getSpace(0,1);

        player1.setSpace(space1);
        player1.setHeading(Heading.EAST);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());
    }

    @Test
    void player1CannotHitPlayer2OutsideMapWest() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        Space space1 = board.getSpace(0,1);
        Space space2 = board.getSpace(11,1);

        player1.setSpace(space1);
        player1.setHeading(Heading.WEST);

        player2.setSpace(space2);
        player2.setHeading(Heading.NORTH);

        gameController.executeBoardElements();

        assertEquals(0, player2.getSpamCards());
    }
}