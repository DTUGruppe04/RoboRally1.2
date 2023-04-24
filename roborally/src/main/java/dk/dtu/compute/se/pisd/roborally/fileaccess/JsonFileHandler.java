package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.roborally.model.Board;

import java.io.*;

public class JsonFileHandler {

    String SaveFile = "roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFile.json";
    Gson Gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    //Constructor Declaration of Class
    public JsonFileHandler() {

    }

    public void saveToSaveFile(Board board) {
        String JSONString = Gson.toJson(board);
        saveToFile(JSONString);
    }

    private void saveToFile(String JSONString) {
        try {
            try (FileWriter SaveWriter = new FileWriter(SaveFile)) {
                SaveWriter.write(JSONString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromSaveFile() {
        String JSONString = "";
            try (FileReader saveReader = new FileReader(SaveFile)){

                BufferedReader bufferedReader = new BufferedReader(saveReader);
                JSONString = bufferedReader.readLine();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
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
