<div align="center">
<h1>Mancala (Kalah) Game</h1>

</div>

<hr />

# Introduction
[**How to Play Mancala?**](https://www.youtube.com/watch?v=OX7rj93m6o8)

## Mancala Rules

### Setup
The Mancala 'board' is made up of two rows of six holes, or pits, each. If you don't have a Mancala board handy, an empty egg carton can work. Next, four pieces -- marbles or stones -- are placed in each of the 12 holes. The color of the pieces is irrelevant.
Each player has a 'store' to the right side of the Mancala board. (Cereal bowls work well for this purpose.)

### Play
The game begins with one player picking up all the pieces in any one of the holes on his side.
Moving counter-clockwise, the player deposits one of the stones in each hole until the stones run out.

1. If you run into your own store, deposit one piece in it. If you run into your opponent's store, skip it.

2. If the last piece you drop is in your own store, you get a free turn.

3. If the last piece you drop is in an empty hole on your side, you capture that piece and any pieces in the hole directly opposite.

4. Always place all captured pieces in your store.

### Winning the game
The game ends when all six spaces on one side of the Mancala board are empty. The player who still has pieces on his side of the board when the game ends captures all of those pieces.
Count all the pieces in each store. The winner is the player with the most pieces.

### Tips:
Planning ahead is essential to victory in board games like Mancala. Try to plan two or three moves into the future.

# Build

## Requirements

For building and running the application you need:

- [JDK 1.8 or higher](https://www.oracle.com/java/technologies/javase/jdk13-archive-downloads.html)
- [Maven 3.5.2](https://maven.apache.org)
- [Docker](https://docs.docker.com/get-docker/)

## Run application

There are several ways to run a Spring Boot application on your local machine.

### Prerequisite for Maven or IDE ways 
>*hint : You do not need these steps if you choose the docker way*

  1.You have to have PostgreSQL Server running on your local machine on default port `5432`
  
  2.You have to run following command ``` CREATE SCHEMA IF NOT EXISTS `mancala`;``` to create schema on your local machine.
### * With Maven




1. Enter the `mancala` folder:  
   ``$ cd mancala``
2. Execute the following maven command to clean the target folder and generate the distributable .jar file **(this command will ignore the unit tests)**:  
   ``$ mvn clean install -DskipTests``
3. Run the jar file with following command.
   ``$ java -jar target/mancala-0.0.1-SNAPSHOT.jar``


### *  With IDE
  This app coded with Intellij IDEA. Open project and run/debug below class.

```shell
MancalaGameApplication.java
```

### *  With Docker and docker-compose (**Recommended**)

Execute the following commands in order to build and generate the docker container images:

1. Enter the `mancala` folder:  
   ``$ cd mancala``
2. Execute the following maven command to clean the target folder and generate the distributable .jar file **(this command will ignore the unit tests)**:  
   ``$ mvn clean install -DskipTests``
3. Execute the Docker build to generate the docker container image:  
   ``$ docker build -t mancala .``
4. Execute the following command to run application with postgres container:  
   ``$ docker-compose up``
   
The Mancala game will be served on ``localhost:8080``. You can play with opponent using two browsers. Enjoy it!

![mancala](https://github.com/abdurrahmankolsuz/mancala/blob/master/mancala.gif)

# LICENSE

[MIT](LICENSE)
