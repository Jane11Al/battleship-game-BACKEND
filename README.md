
# Battleship-game-BACKEND

Backend developed for web-based multiplayer game Battleship that uses Spring. MongoDB was used for storing the data. Clients were supposed to communicate via Websockets.
Unfortunately, the frontend was never finished.
   
    


## Tech Stack

    Spring Boot 2.1.4
    MongoDB 4.0.8
    Websockets used for web-app conversations
    RESTful API


## Model

This project follows MVC pattern. The model `model` package contains all models (both persistant models and DTOs).
The models which are stored in MongoDB are:

    i. Game - contains all data which is used in one game, and also logic for setting ships
    ii. PlayerMatches - contains two player playing the game and their unique socket id


## Controller

The controller is responsible for communicating with the front-end via websockets and REST calls. There are two controllers in this project:

**GameController** 

    i. getScoreboard() -  To get all the player data for making a score-table.
    ii. createPlayer1() - To initialize the first player board and generate random ship positions. It takes username as the parameter.
    iii. getUserName() - To get username based on userId
    iv. createPlayer2() - To initialize the second player. It takes username and the socket-id, which was generated when player1 was initialized.
    v. getPlayer2() - This command returns the player 2 name to be displayed in the player 1 board, via the unique socket-id from the PlayerMatches model.


**WebController**
    
Responsible for communication between the web-apps via websocket. It primarily has just one method, the processMessageFromClient(). It is responsible for all the communication between the clients. Both the clients are subscribed to this socket and send all the information, such as whether the player board is initialized or if the player has taken her turn, etc. Both the client subscribe to the same socket.
## Other
Project contains `repository` package which is used for storing data in MongoDB.
`config` package contains class for further socket configuration (configuring MessageBroker and StompEndpoint)
The `logic` package contains very important `GameConversion` class which is responsible for converting heavy Game class (which uses matrix) into lightweight GameDTO class which is being transfered to frontend (it uses HashMaps and Lists).

## Limitations

i. Frontend was never finished, so the backend  was never fully tested either. 
Especially the part about web sockets and communication

ii. I plan to do the frontend in the near future.

Although the full game is not finished, the main purpose for me was to design MVC pattern, use DTOs and for the first time to try MongoDB.
