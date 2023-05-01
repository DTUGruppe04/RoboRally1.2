package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.view.PremadeMaps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PushPanelTest {
    private GameController gameController;

    //Creating a 8x8 board for testing of PushPanels
    void setUp() {
        //
        PremadeMaps testMap = PremadeMaps.get("Test Map");
        Board board = new Board(testMap.mapArray, "Test Map");
        gameController = new GameController(board);


    }
}
