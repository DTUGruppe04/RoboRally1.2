package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.view.PremadeMaps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class JsonFileHandler {

    String SaveFile = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFile.json";
    Gson Gson = new Gson();

    //Constructor Declaration of Class
    public JsonFileHandler() {

    }

    public void saveToSaveFile(PremadeMaps map, Board board) {
        String JSONStringMap = "[{\"map\": {\"PremadeMaps\": \"" + map + "\"}, ";
        String JSONStringPlayers = "\"players\": {";
        int counter1 = 1;
        String tempPlayerString = "";
        for (Player player : board.getPlayers()) {
            tempPlayerString = "";
            tempPlayerString = tempPlayerString.concat("\"player" + counter1 + "\": {\"name\": \"" + player.getName() + "\", ");
            tempPlayerString = tempPlayerString.concat("\"color\": \"" + player.getColor() + "\", ");
            tempPlayerString = tempPlayerString.concat("\"checkpoints\": " + player.getCheckpoints() + ", ");
            tempPlayerString = tempPlayerString.concat("\"heading\": \"" + player.getHeading() + "\", ");
            tempPlayerString = tempPlayerString.concat("\"space\": { \"x\": " + player.getSpace().x + ", \"y\": " + player.getSpace().y + "}, ");
            tempPlayerString = tempPlayerString.concat("\"Program\": {");
            int counter = 0;
            for (CommandCardField commandCardField : player.getProgram()) {
                if (commandCardField.getCard() == null) {
                    tempPlayerString = tempPlayerString.concat("\"programmingcard" + counter + "\": ");
                    tempPlayerString = tempPlayerString.concat("\"null\"");
                } else {
                    tempPlayerString = tempPlayerString.concat("\"" + commandCardField.getCard().command + "\"");
                }
                counter++;
                if (counter != 5) {
                    tempPlayerString = tempPlayerString.concat(", ");
                }
            }
            counter = 0;
            tempPlayerString = tempPlayerString.concat("}, ");
            tempPlayerString = tempPlayerString.concat("\"Cards\": {");
            for (CommandCardField commandCardField : player.getCards()) {
                tempPlayerString = tempPlayerString.concat("\"commandcard" + counter + "\": ");
                if (commandCardField.getCard() == null) {
                    tempPlayerString = tempPlayerString.concat("\"null\"");
                } else {
                    tempPlayerString = tempPlayerString.concat("\"" + commandCardField.getCard().command + "\"");
                }
                counter++;
                if (counter != 8) {
                    tempPlayerString = tempPlayerString.concat(", ");
                }
            }
            if (counter1 != board.getPlayers().size()) {
                tempPlayerString = tempPlayerString.concat("}}, ");
            } else {
                tempPlayerString = tempPlayerString.concat("}}}]");
            }

            JSONStringPlayers = JSONStringPlayers.concat(tempPlayerString);
            counter1++;
        }
        String JSONString = JSONStringMap + JSONStringPlayers;
        saveToFile(JSONString);
    }

    private void saveToFile(String JSONString) {
        try {
            try (FileWriter SaveWriter = new FileWriter(SaveFile)) {
                System.out.println(JSONString);
                SaveWriter.write(JSONString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Function to save 2D Array to JSON
    public void Save2DArray(String Filepath, int[][] data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        System.out.println("Inside Function");
        try(FileWriter writer = new FileWriter(Filepath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
