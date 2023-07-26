package dk.dtu.compute.se.pisd.roborally.APIhandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.fileaccess.JsonFileHandler;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;
import java.util.Objects;

public class APIhandler implements Runnable {
    JsonFileHandler fileHandler = new JsonFileHandler();
    HttpClient client = HttpClient.newHttpClient();

    GameController gameController;
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
    public void newServer(String APIip, String serverID, int playerNumber) {
        HttpRequest newServer = HttpRequest.newBuilder()
                .uri(URI.create("http://" + APIip + ":8080/Servers" + "?serverID=" + serverID + "&playerNumber=" + (playerNumber)))
                .POST(HttpRequest.BodyPublishers.ofString(fileHandler.readOnlineMapConfig()))
                .build();
        client.sendAsync(newServer, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }

    public String joinServer(String APIip, String serverID) {
        HttpRequest joinServer = HttpRequest.newBuilder()
                .uri(URI.create("http://" + APIip + ":8080/Servers?serverID=" + serverID))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(joinServer, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
        }
        return "";
    }
    public String getPlayerNumber(String APIip, String serverID) {
        HttpRequest getPlayerNumber = HttpRequest.newBuilder()
                .uri(URI.create("http://" + APIip + ":8080/Servers/playerNumber?serverID=" + serverID))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(getPlayerNumber, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
        }
        return "";
    }

    public void updatePlayerOnServer(String APIip, String serverID, String playerNumber){
        HttpRequest updatePlayer = HttpRequest.newBuilder()
                .uri(URI.create("http://" + APIip + ":8080/Servers/Player?serverID=" + serverID + "&playerNumber=" + playerNumber))
                .PUT(HttpRequest.BodyPublishers.ofString(fileHandler.readOnlineMapConfig()))
                .build();
        try {
            client.send(updatePlayer, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMapConfig(String APIip, String serverID) {
        HttpRequest getPlayerNumber = HttpRequest.newBuilder()
                .uri(URI.create("http://" + APIip + ":8080/Servers/mapConfig?serverID=" + serverID))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(getPlayerNumber, HttpResponse.BodyHandlers.ofString());
            fileHandler.updateOnlineMapConfigWithJSONString(response.body());
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
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

    public boolean areAllPlayersReady() {
        JsonObject mapConfig = new JsonParser().parse(fileHandler.readOnlineMapConfig()).getAsJsonObject();
        JsonArray playerArray = mapConfig.getAsJsonArray("players");
        for (JsonElement player: playerArray) {
            if(!player.getAsJsonObject().get("ready").getAsBoolean()){
                System.out.println("yes");
                return false;
            }
        }
        return true;
    }

    public void setAllPlayersReady(boolean bool) {
        for (Player player : gameController.board.getPlayers()) {
            player.setReady(bool);
        }
    }

    @Override
    public void run() {
        if (!gameController.gameHost) {
            do {
                updateMapConfig(AppController.APIIP, AppController.serverID);
                updateBoardFromJSON(fileHandler.readOnlineMapConfig());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (this.gameController.board.getPhase() != Phase.PROGRAMMING || this.gameController.board.getPhase() != Phase.PLAYER_INTERACTION || this.gameController.board.getPhase() != Phase.WINNER);
        } else {
            //check if everyone is in activation phase, if yes: begin the game, if no: keep updating
            do {
                updateMapConfig(AppController.APIIP, AppController.serverID);
                updateBoardFromJSON(fileHandler.readOnlineMapConfig());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (!areAllPlayersReady());
            gameController.finishProgrammingPhase();
        }


    }
}
