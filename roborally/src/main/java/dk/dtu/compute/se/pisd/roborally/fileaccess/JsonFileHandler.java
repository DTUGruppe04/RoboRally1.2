package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileHandler {

    //Constructor Declaration of Class
    public JsonFileHandler() {

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
