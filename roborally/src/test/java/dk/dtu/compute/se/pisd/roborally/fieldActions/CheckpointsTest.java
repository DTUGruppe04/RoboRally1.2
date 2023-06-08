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
public class CheckpointsTest {
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
        for (int i = 0; i < 2; i++) {
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
    void firstCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(0, 1);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();

        assertEquals(1, player1.getCheckpoints());
    }

    @Test
    void secondCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(1, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(2, player1.getCheckpoints());
    }

    @Test
    void thirdCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(1, 1);
        Space space3 = board.getSpace(2, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space3);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(3, player1.getCheckpoints());
    }

    @Test
    void fourthCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(1, 1);
        Space space3 = board.getSpace(2, 1);
        Space space4 = board.getSpace(3, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space3);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space4);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(4, player1.getCheckpoints());
    }

    @Test
    void fifthCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(1, 1);
        Space space3 = board.getSpace(2, 1);
        Space space4 = board.getSpace(3, 1);
        Space space5 = board.getSpace(4, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space3);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space4);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space5);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(5, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 2 when you have no already activated checkpoints.
    @Test
    void falseSecondCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(1, 1);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();

        assertEquals(0, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 3 when you have no already activated checkpoints.
    @Test
    void falseThirdCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(2, 1);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();

        assertEquals(0, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 4 when you have no already activated checkpoints.
    @Test
    void falseFourthCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(3, 1);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();

        assertEquals(0, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 5 when you have no already activated checkpoints.
    @Test
    void falseFifthCheckpointActivateTest1() {
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(4, 1);

        player1.setSpace(space);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();

        assertEquals(0, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 3 when you have already activated checkpoint 1.
    @Test
    void falseThirdCheckpointActivateTest2() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(2, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(1, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 4 when you have already activated checkpoint 1.
    @Test
    void falseFourthCheckpointActivateTest2() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(3, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(1, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 5 when you have already activated checkpoint 1.
    @Test
    void falseFifthCheckpointActivateTest2() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(4, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(1, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 4 when you have already activated checkpoint 1 and 2.
    @Test
    void falseFourthCheckpointActivateTest3() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(1, 1);
        Space space3 = board.getSpace(3, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space3);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(2, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 5 when you have already activated checkpoint 1 and 2.
    @Test
    void falseFifthCheckpointActivateTest3() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(1, 1);
        Space space3 = board.getSpace(4, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space3);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(2, player1.getCheckpoints());
    }

    // Tests whether you can activate checkpoint 5 when you have already activated checkpoint 1 - 3.
    @Test
    void falseFifthCheckpointActivateTest4() {
        Player player1 = board.getPlayer(0);
        Space space1 = board.getSpace(0, 1);
        Space space2 = board.getSpace(1, 1);
        Space space3 = board.getSpace(2, 1);
        Space space4 = board.getSpace(4, 1);


        player1.setSpace(space1);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space2);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space3);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        player1.setSpace(space4);
        player1.setHeading(Heading.SOUTH);

        gameController.moveForward(player1);
        gameController.executeBoardElements();


        assertEquals(3, player1.getCheckpoints());
    }
}
