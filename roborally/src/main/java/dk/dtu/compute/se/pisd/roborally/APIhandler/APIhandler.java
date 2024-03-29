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

public class APIhandler {
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
            client.sendAsync(updatePlayer, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
    }

    public void updateMapConfig(String APIip, String serverID) {
        HttpRequest updateMapconfig = HttpRequest.newBuilder()
                .uri(URI.create("http://" + APIip + ":8080/Servers/mapConfig?serverID=" + serverID))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(updateMapconfig, HttpResponse.BodyHandlers.ofString());
            fileHandler.updateOnlineMapConfigWithJSONString(response.body());
            updateBoardFromJSON(fileHandler.readOnlineMapConfig());
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    public void postMapconfig(String APIip, String serverID) {
        HttpRequest mapConfig = HttpRequest.newBuilder()
                .uri(URI.create("http://" + APIip + ":8080/Servers/mapConfig?serverID=" + serverID))
                .POST(HttpRequest.BodyPublishers.ofString(fileHandler.readOnlineMapConfig()))
                .build();
        client.sendAsync(mapConfig, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
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


}
