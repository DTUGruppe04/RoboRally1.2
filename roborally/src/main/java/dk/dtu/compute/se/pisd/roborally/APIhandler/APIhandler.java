package dk.dtu.compute.se.pisd.roborally.APIhandler;

import dk.dtu.compute.se.pisd.roborally.fileaccess.JsonFileHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class APIhandler {
    JsonFileHandler fileHandler = new JsonFileHandler();
    HttpClient client = HttpClient.newHttpClient();
    public void newServer(String APIip, String serverID) {
        HttpRequest newServer = HttpRequest.newBuilder()
                .uri(URI.create("http://" + APIip + ":8080/Servers" + "?serverID=" + serverID))
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
            return response.toString();
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
        }
        return "";
    }
}
