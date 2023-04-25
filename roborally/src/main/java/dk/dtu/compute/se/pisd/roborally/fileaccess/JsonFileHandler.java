package dk.dtu.compute.se.pisd.roborally.fileaccess;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import javafx.scene.control.ChoiceDialog;
import java.io.*;
import java.util.Optional;

public class JsonFileHandler {

    private final Gson Gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    //Constructor Declaration of Class
    public JsonFileHandler() {}

    /**
     * Saves the current game to a JSON file in one of five possible save slots.
     * @param board the Board object representing the current game state
     */
    public void saveToSaveFile(Board board) {
        String JSONString = Gson.toJson(board);
        String[] saveFiles = new String[]{"Save 1", "Save 2", "Save 3", "Save 4", "Save 5"};
        ChoiceDialog<String> saveFileDialog = new ChoiceDialog<>(saveFiles[0], saveFiles);
        saveFileDialog.setTitle("Save current game");
        saveFileDialog.setHeaderText("Which save do you want to save your game on.");
        Optional<String> saveFileNameOption = saveFileDialog.showAndWait();
        if (saveFileNameOption.isPresent()) {
            String saveFileName = saveFileNameOption.get();
            String SaveFilePath = saveFileSwitch(saveFileName);
            writeToJSONFile(JSONString, SaveFilePath);
        }
    }

    /**
     * Prompts the user to select a save file to load and returns the contents of the selected file as a JSON string.
     * @return a JSON string representing the contents of the selected save file, or an empty string if no file was selected
     */
    public String readFromSaveFile() {
        String JSONString = "";
        String[] saveFiles = new String[]{"Save 1", "Save 2", "Save 3", "Save 4", "Save 5"};
        ChoiceDialog<String> saveFileDialog = new ChoiceDialog<>(saveFiles[0], saveFiles);
        saveFileDialog.setTitle("Load from save file");
        saveFileDialog.setHeaderText("Which save game do you want to load");
        Optional<String> saveFileNameOption = saveFileDialog.showAndWait();
        if (saveFileNameOption.isPresent()) {
            String saveFileName = saveFileNameOption.get();
            String SaveFilePath = saveFileSwitch(saveFileName);
            JSONString = readFromJSONFile(SaveFilePath);
        }
        return JSONString;
    }

    //Used for the read and write, from and to save file methods
    private String saveFileSwitch(String saveFileNameOption) {
        String saveFile1 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile1.json";
        String saveFile2 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile2.json";
        String saveFile3 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile3.json";
        String saveFile4 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile4.json";
        String saveFile5 = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile5.json";
        String SaveFilePath = saveFile1;
        switch (saveFileNameOption) {
            case "Save 1" -> SaveFilePath = saveFile1;
            case "Save 2" -> SaveFilePath = saveFile2;
            case "Save 3" -> SaveFilePath = saveFile3;
            case "Save 4" -> SaveFilePath = saveFile4;
            case "Save 5" -> SaveFilePath = saveFile5;
        }
        return SaveFilePath;
    }

    /**
     * Writes the inserted JSON String to the file path inserted
     * @param JSONString, the JSON String to write to a file
     * @param writeFilePath, the filepath to the file you need to write to
     */
    private static void writeToJSONFile(String JSONString, String writeFilePath) {
        try {
            try (FileWriter fileWriter = new FileWriter(writeFilePath)) {
                fileWriter.write(JSONString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Whole JSON string has to be in one line, on line 1
     * @param readFilePath, the filepath to the JSON file you want to read from
     * @return The JSON from the file as a string
     */
    private static String readFromJSONFile(String readFilePath) {
        String JSONString;
        try (FileReader fileReader = new FileReader(readFilePath)){

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            JSONString = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSONString;
    }

}
