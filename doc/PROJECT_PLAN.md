# Project Plan

## Feature Priorities

For our midpoint demo, we would like to be able create and run Tic Tac Toe. This would involve being able to create the FCM graph from the authoring environment (showing 2 nodes) and personalizing the grid and icons. The engine should be able to detect both a tie or a win, restart, and only allow valid moves (not placing a mark on a location that's already been marked, etc.). 

We would also like to demo the creation of a game with sprites of different capabilities and movements (like chess, though we would demo a simpler version in the interest of time). In this game, we would also like to show how sprites can move across the grid, as well as how player (or sprite) stats can be dynamically changed in response to movement.

## Team Responsibilities 

* Natalie Le (nl121)
    * **Primary Responsibility:** Game Engine
    * **Secondary Responsibility:** Game Player, Game Data
    * **First Sprint**: During this sprint, I will implement the traversal of the FCM by querying the Behavior class and dynamically appending or removing appropriate Tokens. I will also implement validity checks for moves (based on rules) and for game completion (based on objectives). Finally, I will implement displaying the behaviors and animations defined by the author. 
    * **Second Sprint**: I will work on implementing the *social center* extension. This involves allowing users to log in, choose avatars, save scores, and select preferences.

* Vincent Liu (jl729)
    * **Primary Responsibility:** Game Authoring Engine
    * **Secondary Responsibility:** Game Player
    * **First Sprint**: During this sprint, I will create a framework for front end and design the internal and external APIs for front end. I will work on Controller class and deal with the interactions with the back end. I will assit on creating Game Player and its actions when events occur during the game (e.g. collisions).
    * **Second Sprint**: I will work on the _Shared Editor_ that allows multiple people to edit a game's design together

* Haotian Wang (hw186)
    * **Primary Responsibility:** Game Player (front end)
    * **Secondary Responsibility:** Game Authoring Engine (front end)
    * **First Sprint:** During this sprint, I will develop the front end framework, design and APIs for the game player, which will support basic functionalities such as dynamically showing "scores" and presenting user-defined images and icons. I will also work on maintaining consistency across the two front end modules and ensure the style of encapsulation across front end packages are easy to understand and maintain. I will also work with back end teams regarding saving game progress and reloading game progress.
    * **Second Sprint:** I will work on the interactive editing of gaming content while the game is running --- Live Game Editing

* Jason Zhou (jz192)
    * **Primary Responsibility:** Game Authoring Environment (back end)
    * **Secondary Responsibility:** Game Engine (back end)
    * **First Sprint:** During the sprint, I will implement the back end of the game authoring environment to allow users to create simple turn-based games such as tic-tac-toe and checkers. I will design the framework and develop the functionalities of the Tile and Entity classes to allow authors to create game logic based on the game states.
    * **Second Sprint:** I will work on extending the basic functionalities of the editing environment to support multi-user editing.
    
* Amy Kim (yk154)
    * **Primary Responsibility:** Game Authoring Engine (front end)
    *  **Secondary Responsibility:** Game Player (front end)
    *  **First Sprint:** During this sprint, I will work on the frontend framework and utilities. Primarily aim to work on the basic functionalites of Edit View in Game Authoring Engine (e.g. Event handling between different views and multiple edit views.) and Data Interfaces.
    *  **Second Sprint:** I will work on extending the basic functionalities and UI interfaces to support any extenstion (e.g. Social Center extension)

* Inchan Hwang (ih33)

    * **Primary Responsibility:** Game Authoring Engine (backend)
    * **Secondary Responsibility:** Game Authoring Engine (frontend)
    * **First Sprint:**
        * Build the core functionalities of our design without too many convenience features. The core functionalities include ...
            * Converting BlockCode into Groovy code.
            * Allow the author to build the fsm. 
    * **Second Sprint:**
        * Add in many many convenience features (undo, ... etc) that frontend planned.

* Yunhao Qing (yq50)
    * **Primary Responsibility:** Game Engine (backend)
    * **Secondary Responsibility:** Game Engine (backend)
    * **First Sprint:** For the first sprint, I will work on the game engine to accept grovvy code and link the code to the tiles and sprites.
    * **Second Sprint:** I will work on functionalities to allow game play over networks.

* Jonathan Nakagawa (jyn2)
    * **Primary Responsibility:** Game Engine (backend)
    * **Secondary Responsibility:** Game Engine (backend)
    * **First Sprint:** For the first sprint, I will work on the game engine to have grovvy code interface with the java backend.
    * **Second Sprint:** I will work on implementing AI.