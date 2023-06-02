package dk.dtu.compute.se.pisd.roborally.connectionHandlers;

import java.net.*;
import java.io.*;

public class Server {
    //initialize socket and input stream
    private Socket[] clientArray;
    private ServerSocket server;
    private DataInputStream[] clientInArray;
    private DataOutputStream[] clientOutArray;

    // constructor with port and player number
    public Server(int port, int playerNumber) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for clients ...");
            clientArray = new Socket[playerNumber - 1];
            clientInArray = new DataInputStream[playerNumber - 1];
            clientOutArray = new DataOutputStream[playerNumber - 1];
            for (int i = 0; i < playerNumber - 1; i++) {
                clientArray[i] = server.accept();
                System.out.println("Client joined server");
                System.out.println("Waiting for clients ...");
            }
            for (int j = 0; j < playerNumber - 1; j++) {
                clientInArray[j] = new DataInputStream(
                        new BufferedInputStream(clientArray[j].getInputStream()));
            }
            for (int k = 0; k < playerNumber - 1; k++) {
                clientOutArray[k] = new DataOutputStream(clientArray[k].getOutputStream());
            }
            System.out.println("All clients joined successfully");
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void readInputs() {
        String[] inputs = new String[clientArray.length];
        // reads message from client forever, should be fixed
        while (true) {
            try {
                for (int i = 0; i < inputs.length; i++) {
                    inputs[i] = clientInArray[i].readUTF();
                }
                // sends "Message received" to client when responses is received
                for (int j = 0; j < inputs.length; j++) {
                    clientOutArray[j].writeUTF("Message received");
                }
                for (String input : inputs) {
                    System.out.println(input);
                }
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    public void closeServer() {
        System.out.println("Closing connection");
        // close connection
        try {
            for (int k = 0; k < clientArray.length; k++) {
                clientArray[k].close();
                clientInArray[k].close();
                clientOutArray[k].close();
            }
            server.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}