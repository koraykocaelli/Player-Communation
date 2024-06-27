package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Player class that handles the communication between players.
 * Each instance can send and receive messages, keeping track of message count.
 */
public class Player implements Runnable {
    private Socket socket;
    private String name;
    private BufferedReader in;
    private PrintWriter out;
    private int messageCount = 0;

    public Player(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.name = name;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(name + " initialized.");
    }

    public void sendMessage(String message) {
        System.out.println(name + " sending: " + message);
        out.println(message);
    }

    @Override
    public void run() {
        try {
            if (name.equals("Player1Client")) {
                sendMessage("Hello");  // Initial message from Player1Client
            }

            String receivedMessage;
            while ((receivedMessage = in.readLine()) != null) {
                System.out.println(name + " received: " + receivedMessage);
                messageCount++;
                if (messageCount >= 10) {
                    break;
                }
                sendMessage(receivedMessage + " " + messageCount);
            }

            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
