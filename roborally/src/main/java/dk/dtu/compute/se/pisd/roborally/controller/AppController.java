/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

import dk.dtu.compute.se.pisd.roborally.model.*;

import dk.dtu.compute.se.pisd.roborally.view.PremadeMaps;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

import dk.dtu.compute.se.pisd.roborally.fileaccess.JsonFileHandler;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class AppController implements Observer {

    /**
     The list of available player numbers to choose from when starting a new game.
     */
    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);

    /**
     The list of available player colors to choose from when starting a new game.
     */
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    /**
     The RoboRally object that contains the application's data model.
     */
    final private RoboRally roboRally;

    /**
     The GameController object that manages the current game.
     */
    private GameController gameController;

    private PremadeMaps map;
    private final JsonFileHandler jsonFileHandler = new JsonFileHandler();
    /**
     Creates a new AppController object with the specified RoboRally object.
     @param roboRally the RoboRally object to use as the application's data model
     */
    public AppController(@NotNull RoboRally roboRally) throws IOException {
        this.roboRally = roboRally;
    }

    /**
     * Starts a new game with the specified number of players.
     * If a game is already in progress, the user is given the option to save the game or abort the operation.
     * If the user chooses to save the game, the current game is saved and the new game is started.
     * If the user chooses to abort the operation, the method returns without starting a new game.
     */
    public void newGame() {
        ChoiceDialog<Integer> playerNumberDialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        playerNumberDialog.setTitle("Player number");
        playerNumberDialog.setHeaderText("Select number of players");
        Optional<Integer> playerNumberResult = playerNumberDialog.showAndWait();

        ArrayList<PremadeMaps> mapChoices = new ArrayList<>();
        ArrayList<String> mapChoicesName = new ArrayList<>();
        for (int i = 0; i < PremadeMaps.values().length; i++) {
           mapChoices.add(PremadeMaps.get(i));
           mapChoicesName.add(PremadeMaps.get(i).mapName);
        }
        ChoiceDialog<String> mapChoiceDialog = new ChoiceDialog<>(mapChoicesName.get(0), mapChoicesName);
        mapChoiceDialog.setTitle("Map type");
        mapChoiceDialog.setHeaderText("Select map type");
        Optional<String> mapChoiceResultName = mapChoiceDialog.showAndWait();
        PremadeMaps mapChoiceResult = null;
        if (mapChoiceResultName.isPresent()) {
            for (PremadeMaps map: mapChoices) {
                if (map.mapName.equals(mapChoiceResultName.get())) {
                    mapChoiceResult = map;
                }
            }
        }
        this.map = mapChoiceResult;
        if (playerNumberResult.isPresent() && mapChoiceResultName.isPresent() && mapChoiceResult != null) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }

            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.


            Board board = new Board(mapChoiceResult.mapArray, mapChoiceResult.mapName);
            gameController = new GameController(board);
            int no = playerNumberResult.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    /**
     * Saves the current game state.
     */
    public void saveGame() {
        jsonFileHandler.saveToSaveFile(this.gameController.board);
    }

    /**
     * Loads a saved game state.
     * If there is no saved game state, a new game is started.
     */
    public void loadGame() {
        //loads string from savefile and creates a JSON object from the json string
        String stringSaveFile = jsonFileHandler.readFromSaveFile();
        if (!stringSaveFile.equals("")) {
            JsonObject SaveFile = new JsonParser().parse(stringSaveFile).getAsJsonObject();
            //finds the map in the savefile and creates a new board with the given map
            PremadeMaps map = PremadeMaps.get(SaveFile.get("boardName").getAsString());
            Board board = new Board(map.mapArray, map.mapName);
            //creates a gamecontroller from the new board
            this.gameController = new GameController(board);
            this.map = map;
            //creates a JsonArray from the savefile with all the players in the saved game
            JsonArray jsonArray = SaveFile.getAsJsonArray("players");
            //iterates through each jsonplayer in the jsonplayerarray
            for(JsonElement jsonplayer : jsonArray) {
                //the player as a json object
                JsonObject tempJsonPlayer = jsonplayer.getAsJsonObject();
                //creates a player
                Player player = new Player(board, tempJsonPlayer.get("color").getAsString(), tempJsonPlayer.get("name").getAsString());
                //adds the new player to the board, sets heading, amount of checkpoints reached and placement on the board for the given player
                board.addPlayer(player);
                player.setHeading(Heading.get(tempJsonPlayer.get("heading").getAsString()));
                player.setSpace(board.getSpace(
                        tempJsonPlayer.get("space").getAsJsonObject().get("x").getAsInt(),
                        tempJsonPlayer.get("space").getAsJsonObject().get("y").getAsInt()));
                player.setCheckpoints(tempJsonPlayer.get("checkpoints").getAsInt());
                //iterates through the saved program and cards, and adds the programming cards to the right commandcardfield in the players program and cards
                getCardAndAddToFieldFromJson(tempJsonPlayer.getAsJsonArray("program"), player, "program");
                getCardAndAddToFieldFromJson(tempJsonPlayer.getAsJsonArray("cards"), player, "cards");
            }
            //sets the games phase to the saved phase, sets the currentplayer to player on, sets the step of the game to the current step and opens the game
            this.gameController.board.setPhase(Phase.get(SaveFile.get("phase").getAsString()));
            this.gameController.board.setCurrentPlayer(this.gameController.board.getPlayer(0));
            this.gameController.board.setStep(SaveFile.get("step").getAsInt());
            roboRally.createBoardView(this.gameController);
        }
    }

    private void getCardAndAddToFieldFromJson(JsonArray cardsJson, Player player, String programOrCards) {
        int cardCounter = 0;
        CommandCardField field;
        for (JsonElement cardJson : cardsJson) {
            if (programOrCards.equals("program")) {
                field = player.getProgramField(cardCounter);
            } else {
                field = player.getCardField(cardCounter);
            }
            if (cardJson.getAsJsonObject().get("card") != null) {
                Command savedCommand = Command.get(cardJson.getAsJsonObject().get("card").getAsJsonObject().get("command").getAsString());
                CommandCard savedCommandCard = new CommandCard(Objects.requireNonNull(savedCommand));
                field.setCard(savedCommandCard);
            } else {
                field.setCard(null);
            }
            field.setVisible(true);
            cardCounter++;
        }
    }

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    /**
     * Exits the application, giving the user the option to save the game or cancel exiting the application.
     * If the user chooses to save the game, the game is saved and the application is exited.
     * If the user chooses to cancel exiting the application, the method returns without exiting the application.
     * If there is no current game, the application is exited without giving the option to save the game.
     */
    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }

    /**
     * Returns true if a game is currently in progress, false otherwise.
     *
     * @return true if a game is currently in progress, false otherwise.
     */
    public boolean isGameRunning() {
        return gameController != null;
    }


    /**
     * This method is called by the subject when a change has occurred.
     * For now, this method does nothing.
     *
     * @param subject the subject that changed
     */
    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

}
