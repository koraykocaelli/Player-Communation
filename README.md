# Player Communication

## Overview

This project demonstrates communication between two players using sockets in Java. The main goal is to create two player instances that can send and receive messages between each other. 
The initiator player sends a message to the second player, and the second player responds by concatenating the received message with a message counter. 
The program terminates gracefully after the initiator has sent and received 10 messages.

## Requirements

- Java 17 or higher
- Maven

## How to Run

1. Build the project using Maven: mvn clean package
2. Run the project: ./run.sh

## Responsibilities of Each Class

- **Main.java**: 
  - Sets up the server sockets for the players.
  - Manages the connections between players.
  - Starts the player threads and the client connections.

- **Player.java**: 
  - Handles sending and receiving messages.
  - Manages message count and ensures the program terminates after 10 messages.
