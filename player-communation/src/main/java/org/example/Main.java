package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main class that sets up the server sockets and manages the players' connections.
 * It creates two players and ensures communication between them.
 */
public class Main {
    public static void main(String[] args) {
        int player1Port = 6000; // Port number for Player1
        int player2Port = 6001; // Port number for Player2

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try {
            ServerSocket player1ServerSocket = new ServerSocket(player1Port);
            ServerSocket player2ServerSocket = new ServerSocket(player2Port);

            // Accept connections for Player1
            new Thread(() -> {
                try {
                    while (true) {
                        System.out.println("Waiting for Player1 to connect...");
                        Socket player1Socket = player1ServerSocket.accept();
                        System.out.println("Player1 connected");
                        executorService.submit(new Player(player1Socket, "Player1"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Accept connections for Player2
            new Thread(() -> {
                try {
                    while (true) {
                        System.out.println("Waiting for Player2 to connect...");
                        Socket player2Socket = player2ServerSocket.accept();
                        System.out.println("Player2 connected");
                        executorService.submit(new Player(player2Socket, "Player2"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Player1Client connecting to Player2
            new Thread(() -> {
                try {
                    System.out.println("Player1Client attempting to connect to Player2...");
                    Socket player1ToPlayer2Socket = new Socket("localhost", player2Port);
                    Player player1Client = new Player(player1ToPlayer2Socket, "Player1Client");
                    executorService.submit(player1Client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Player2Client connecting to Player1
            new Thread(() -> {
                try {
                    System.out.println("Player2Client attempting to connect to Player1...");
                    Socket player2ToPlayer1Socket = new Socket("localhost", player1Port);
                    Player player2Client = new Player(player2ToPlayer1Socket, "Player2Client");
                    executorService.submit(player2Client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Shutdown hook to gracefully shut down the executor service
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down executor service...");
            executorService.shutdown();
        }));
    }
}
