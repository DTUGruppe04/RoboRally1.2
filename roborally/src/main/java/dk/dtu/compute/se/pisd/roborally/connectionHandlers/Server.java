package dk.dtu.compute.se.pisd.roborally.connectionHandlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.fileaccess.JsonFileHandler;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.net.*;
import java.io.*;
import java.util.Objects;

public class Server implements Runnable{
    //initialize socket and input stream
    private Socket[] clientArray;
    private ServerSocket server;
    private DataInputStream[] clientInArray;
    private DataOutputStream[] clientOutArray;
    private final int port;
    private final int playerNumber;
    public int threadNumber = 0;
    String[] inputs;
    private final JsonFileHandler jsonFileHandler = new JsonFileHandler();
    private GameController gameController;

    // constructor with port and player number
    public Server(int port, int playerNumber, GameController gameController){
        this.port = port;
        this.playerNumber = playerNumber;
        this.gameController = gameController;
    }

    public void readInputs() {
        inputs = new String[clientArray.length];
        // reads message from client forever, should be fixed
        try {
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = clientInArray[i].readUTF();
            }
            // sends "Message received" to client when responses is received
            for (String input : inputs) {
                System.out.println(input);
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void closeServer() {
        System.out.println("Closing connection");
        // close connection
        try {
            for (int k = 0; k < clientArray.length; k++) {
                clientArray[k].close();
                clientInArray[k].close();
                clientOutArray[k].close();
            }
            server.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    @Override
    public void run() {
        if (threadNumber == 0) {
            // starts server and waits for a connection
            try {
                server = new ServerSocket(port);
                System.out.println("Server started");

                System.out.println("Waiting for clients ...");
                clientArray = new Socket[playerNumber - 1];
                clientInArray = new DataInputStream[playerNumber - 1];
                clientOutArray = new DataOutputStream[playerNumber - 1];
                for (int i = 0; i < playerNumber - 1; i++) {
                    clientArray[i] = server.accept();
                    clientInArray[i] = new DataInputStream(
                            new BufferedInputStream(clientArray[i].getInputStream()));
                    clientOutArray[i] = new DataOutputStream(clientArray[i].getOutputStream());
                    clientOutArray[i].writeUTF(String.valueOf(i+1));
                    clientOutArray[i].writeUTF(jsonFileHandler.readOnlineMapConfig());
                    System.out.println("Client joined server");
                    System.out.println("Waiting for clients ...");
                }
                System.out.println("All clients joined successfully");
            } catch (IOException i) {
                System.out.println(i);
            }
            threadNumber = 1;
        } else if (threadNumber == 1) {
            readInputs();
            for (int i = 0; i < playerNumber-1; i++) {
                JsonObject mapConfigFromClient = new JsonParser().parse(inputs[i]).getAsJsonObject();
                JsonArray jsonArray = mapConfigFromClient.getAsJsonArray("players");
                JsonElement jsonPlayer = jsonArray.get(i+1);
                JsonObject tempJsonPlayer = jsonPlayer.getAsJsonObject();
                Player player = gameController.board.getPlayer(i+1);
                player.setHeading(Heading.get(tempJsonPlayer.get("heading").getAsString()));
                player.setSpace(gameController.board.getSpace(
                        tempJsonPlayer.get("space").getAsJsonObject().get("x").getAsInt(),
                        tempJsonPlayer.get("space").getAsJsonObject().get("y").getAsInt()));
                player.setCheckpoints(tempJsonPlayer.get("checkpoints").getAsInt());
                //iterates through the saved program and cards, and adds the programming cards to the right commandcardfield in the players program and cards
                getCardAndAddToFieldFromJson(tempJsonPlayer.getAsJsonArray("program"), player, "program");
                getCardAndAddToFieldFromJson(tempJsonPlayer.getAsJsonArray("cards"), player, "cards");
            }
            gameController.setActivationPhase();
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

    public void POSTall(String message) {
        try {
            for (int i = 0; i < clientOutArray.length; i++) {
                clientOutArray[i].writeUTF(message);
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}