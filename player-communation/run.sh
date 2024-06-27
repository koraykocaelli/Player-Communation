#!/bin/sh
mvn clean package
java -cp target/player-communication-1.0-SNAPSHOT.jar org.example.Main
chmod +x run.sh
