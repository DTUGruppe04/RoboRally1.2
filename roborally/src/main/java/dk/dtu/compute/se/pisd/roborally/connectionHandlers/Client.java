package dk.dtu.compute.se.pisd.roborally.connectionHandlers;

import dk.dtu.compute.se.pisd.roborally.fileaccess.JsonFileHandler;

import java.io.*;
import java.net.*;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataInputStream serverInput = null;
    private DataOutputStream out = null;
    public int playerNumber;
    private final JsonFileHandler jsonFileHandler = new JsonFileHandler();

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);
            serverInput = new DataInputStream(socket.getInputStream());

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            String playerNumberString = serverInput.readUTF();
            System.out.println("Connected");
            playerNumber = Integer.parseInt(playerNumberString);
            jsonFileHandler.updateOnlineMapConfigWithJSONString(serverInput.readUTF());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }
    }

    public void POST(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public String recieveFromServer() {
        try {
            return serverInput.readUTF();
        } catch (IOException i) {
            System.out.println(i);
        }
        return "";
    }
}
