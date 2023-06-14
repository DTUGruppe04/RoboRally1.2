package dk.dtu.compute.se.pisd.roborally.connectionHandlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.fileaccess.JsonFileHandler;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.io.*;
import java.net.*;
import java.util.Objects;

import static dk.dtu.compute.se.pisd.roborally.controller.AppController.client;

public class Client implements Runnable{
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataInputStream serverInput = null;
    private DataOutputStream out = null;
    private volatile boolean interactionStop = false;
    public int playerNumber;
    private final JsonFileHandler jsonFileHandler = new JsonFileHandler();
    private GameController gameController;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);
            serverInput = new DataInputStream(socket.getInputStream());

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            String playerNumberString = serverInput.readUTF();
            System.out.println("Connected");
            playerNumber = Integer.parseInt(playerNumberString);
            jsonFileHandler.updateOnlineMapConfigWithJSONString(serverInput.readUTF());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void POST(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public String recieveFromServer() {
        String server = "";
        try {
            server = serverInput.readUTF();
        } catch (IOException i) {
            System.out.println(i);
        }
        return server;
    }

    @Override
    public void run() {

        String serverInput = client.recieveFromServer();
        //System.out.println(serverInput);
        while (!serverInput.equals("END ACTIVATION")) {
            jsonFileHandler.updateOnlineMapConfigWithJSONString(serverInput);
            gameController.executeStep();
            updateBoardFromJSON(jsonFileHandler.readOnlineMapConfig());

            if (gameController.board.getPhase() == Phase.PLAYER_INTERACTION && gameController.board.getCurrentPlayer() == gameController.board.getPlayer(playerNumber)) {
                while (!interactionStop) {
                    Thread.onSpinWait();
                    //empty while loop
                }
                jsonFileHandler.updateOnlineMapConfigWithBoard(gameController.board);
                POST(jsonFileHandler.readOnlineMapConfig());
                interactionStop = false;
            } else if (gameController.board.getPhase() == Phase.PLAYER_INTERACTION && !gameController.board.getCurrentPlayer().getName().equals("Player 1") ) {
                jsonFileHandler.updateOnlineMapConfigWithBoard(gameController.board);
                POST(jsonFileHandler.readOnlineMapConfig());
            }
            serverInput = client.recieveFromServer();
        }
        System.out.println("out of while loop");
        if(serverInput.equals("END ACTIVATION")) {
            gameController.startProgrammingPhase();
        }
    }

    private void updateBoardFromJSON(String JSONString) {
        if (!JSONString.equals("")) {
            JsonObject SaveFile = new JsonParser().parse(JSONString).getAsJsonObject();
            //creates a JsonArray from the savefile with all the players in the saved game
            JsonArray jsonArray = SaveFile.getAsJsonArray("players");
            //iterates through each jsonplayer in the jsonplayerarray
            for(JsonElement jsonplayer : jsonArray) {
                //the player as a json object
                JsonObject tempJsonPlayer = jsonplayer.getAsJsonObject();
                //creates a player
                int playerNumber = tempJsonPlayer.get("name").getAsString().charAt(tempJsonPlayer.get("name").getAsString().length()-1)-48;
                Player player = gameController.board.getPlayers().get(playerNumber-1);
                player.setHeading(Heading.get(tempJsonPlayer.get("heading").getAsString()));
                player.setSpace(gameController.board.getSpace(
                        tempJsonPlayer.get("space").getAsJsonObject().get("x").getAsInt(),
                        tempJsonPlayer.get("space").getAsJsonObject().get("y").getAsInt()));
                player.setCheckpoints(tempJsonPlayer.get("checkpoints").getAsInt());
                player.setSpamCards(tempJsonPlayer.get("spamCards").getAsInt());
                //iterates through the saved program and cards, and adds the programming cards to the right commandcardfield in the players program and cards
                getCardAndAddToFieldFromJson(tempJsonPlayer.getAsJsonArray("program"), player, "program");
                getCardAndAddToFieldFromJson(tempJsonPlayer.getAsJsonArray("cards"), player, "cards");
            }
            //sets the games phase to the saved phase, sets the currentplayer to player on, sets the step of the game to the current step and opens the game
            gameController.board.setPhase(Phase.get(SaveFile.get("phase").getAsString()));
            gameController.board.setStep(SaveFile.get("step").getAsInt());
            JsonObject currentPlayer = SaveFile.get("current").getAsJsonObject();
            int playerNumber = currentPlayer.get("name").getAsString().charAt(currentPlayer.get("name").getAsString().length()-1)-48;
            Player player = gameController.board.getPlayers().get(playerNumber-1);
            gameController.board.setCurrentPlayer(player);
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
    public void setInteractionStop(boolean interactionStop) { this.interactionStop = interactionStop; }
}
