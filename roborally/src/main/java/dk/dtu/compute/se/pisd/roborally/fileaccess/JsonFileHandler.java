package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import javafx.scene.control.ChoiceDialog;

import java.io.*;
import java.util.Optional;

public class JsonFileHandler {

    String SaveFile1 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile1.json";
    String SaveFile2 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile2.json";
    String SaveFile3 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile3.json";
    String SaveFile4 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile4.json";
    String SaveFile5 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile5.json";
    String[] saveFiles = new String[]{"Save 1", "Save 2", "Save 3", "Save 4", "Save 5"};
    Gson Gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    //Constructor Declaration of Class
    public JsonFileHandler() {

    }

    public void saveToSaveFile(Board board) {
        String JSONString = Gson.toJson(board);
        saveToFile(JSONString);
    }

    private void saveToFile(String JSONString) {
        ChoiceDialog<String> saveFileDialog = new ChoiceDialog<>(saveFiles[0], saveFiles);
        saveFileDialog.setTitle("Save current game");
        saveFileDialog.setHeaderText("Which save do you want to save your game on.");
        Optional<String> saveFileNameOption = saveFileDialog.showAndWait();
        if (saveFileNameOption.isPresent()) {
            String saveFileName = saveFileNameOption.get();
            String SaveFile = SaveFile1;
            switch (saveFileName) {
                case "Save 1" -> SaveFile = SaveFile1;
                case "Save 2" -> SaveFile = SaveFile2;
                case "Save 3" -> SaveFile = SaveFile3;
                case "Save 4" -> SaveFile = SaveFile4;
                case "Save 5" -> SaveFile = SaveFile5;
            }
            try {
                try (FileWriter SaveWriter = new FileWriter(SaveFile)) {
                    SaveWriter.write(JSONString);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String readFromSaveFile() {
        String JSONString = "";
        ChoiceDialog<String> saveFileDialog = new ChoiceDialog<>(saveFiles[0], saveFiles);
        saveFileDialog.setTitle("Load from save file");
        saveFileDialog.setHeaderText("Which save game do you want to load");
        Optional<String> saveFileNameOption = saveFileDialog.showAndWait();
        if (saveFileNameOption.isPresent()) {
            String saveFileName = saveFileNameOption.get();
            String SaveFile = SaveFile1;
            switch (saveFileName) {
                case "Save 1" -> SaveFile = SaveFile1;
                case "Save 2" -> SaveFile = SaveFile2;
                case "Save 3" -> SaveFile = SaveFile3;
                case "Save 4" -> SaveFile = SaveFile4;
                case "Save 5" -> SaveFile = SaveFile5;
            }
            try (FileReader saveReader = new FileReader(SaveFile)){

                BufferedReader bufferedReader = new BufferedReader(saveReader);
                JSONString = bufferedReader.readLine();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return JSONString;
    }

    public String loadFromSaveFile() {
        return readFromSaveFile();
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
