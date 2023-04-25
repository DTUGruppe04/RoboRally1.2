package dk.dtu.compute.se.pisd.roborally.fileaccess;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonFileHandlerTest {

    @Test
    void saveFileSwitchTest() {
        JsonFileHandler handler = new JsonFileHandler();
        String saveFile = "Save 3";
        String filePath = handler.saveFileSwitch(saveFile);
        assertEquals("roborally/src/main/java/dk/dtu/compute/se/pisd/SaveFiles/SaveFile3.json", filePath);
    }

    @Test
    void writeToJSONFileTest() {
        String jsonString = "{\"name\": \"test\"}";
        String filePath = "test.json";
        assertDoesNotThrow(() -> JsonFileHandler.writeToJSONFile(jsonString, filePath));
    }

    @Test
    void readFromJSONFileTest() {
        String jsonString = JsonFileHandler.readFromJSONFile("test.json");
        assertNotNull(jsonString);
    }
}