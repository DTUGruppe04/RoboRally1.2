package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.view.PremadeMaps;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class PushPanelTest {
    private GameController gameController;
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    @BeforeEach
    void setUp() {
        //Choosing our test map
        PremadeMaps testMap = PremadeMaps.get("Test Map");
        Board board = new Board(testMap.mapArray, testMap.mapName);
        gameController = new GameController(board);


        //Creating 2 players and add them to the board
        for(int i = 0; i < 2; i++) {
            Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
            board.addPlayer(player);
        }
        //Spawning the players in the game
        gameController.spawnPlayers();
        gameController.startProgrammingPhase();

    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void pushPlayerRightTest() {
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Space space = board.getSpace(9, 1);

        player1.setSpace(space);
        player1.setHeading(Heading.NORTH);

        //Making sure the player is standing on the right space.
        assertEquals(space, player1.getSpace());
    }
}
