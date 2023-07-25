package dk.dtu.compute.se.pisd.roborally.APIhandler;

import dk.dtu.compute.se.pisd.roborally.fileaccess.JsonFileHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;

public class APIhandler {
    JsonFileHandler fileHandler = new JsonFileHandler();
    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/GET/greeting"))
            .build();

    HttpRequest response = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/POST/greeting"))
            .POST(HttpRequest.BodyPublishers.ofString(fileHandler.readOnlineMapConfig()))
            .build();
    public void test() {
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
        client.sendAsync(response, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}
