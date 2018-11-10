# Design Plan

## Introduction

For this project, our team will build an authoring environment and engine for team-based strategy games. Features of this game genre include sequential turns, the common manipulation of some shared space or model (whether it be a grid, like in checkers, or a layout in which you draw/play cards, like in Fluxx). 

Our design will need to support the ability to dynamically change the turn sequence (such as by skipping players or reversing direction), define multiple objectives (such as winning via elimination or attrition), create sprites of different function (like the pieces in chess), and manipulate the shared model (like moving pieces across the board). 

 -the primary design goals of the project (i.e., where is it most flexible)
 
We hope to design the game authoring environment and game engine in such a way to provide the author with the most flexibility in terms of the behavior and logic of the entities in a turn-based game.

 -the primary architecture of the design (i.e., what is closed and what is open)*

The front-end elements in the game authoring engine will support modularized design that allows users as much as possible, to customize their workspaces. They will be allowed to add/toggle different editing panels at different layers of the authoring interface, very much like PhotoShop. The back-end of the game authoring environment will provide the game author with tiles and grid. They are both configurable in terms of appearance and logic that are triggered by user-inputs.

## Overview

### Game Authoring Engine

The game authoring engine allows the author to have access to a pane, which consists of premade list of possible tiles. 

On another tab, the author can define entities that this game consists of - their (class name, instance id) and other custom attributes that users can set. Also, the authors would be required to set a default sprite for each entities, but they can also add custom logic that tells it to display different sprites depending on its instance attributes. Within the programming pane, the author can reference each instance of each class through (class name, instance id) pair. 

Once in the pane, the author can choose from a list of possible user inputs and define the possible effects each chosen input would have on the entity. In addition, there would also be a default option which does not depend on user inputs.

Once the authoring process is complete, the sprites, tiles and code blocks are mapped onto a xml file, and is stored in a folder together with the image resources.

Once the rules, objectives, and sprites are defined within the authoring environment, the game engine will populate its Finite Change Machine (FCM) graph with nodes representing behaviors in the game, as well as its Behavior class with appropriate actions to take at each node (such as querying the user for input, etc.) and which Token to append to the stack. This list of Tokens is used the traverse the FCM and, consequently, control the sequence of the game.

## User Interface

* This is the Edit Window where represents the game setting.

![editWindow UI](editWindow.png "Edit Window UI")

* This is pop up screen when the user wants to add entities. 

![sideView UI](TreeviewPopUp.png "Side View UI")

* When the user clicks run from the menu, the scene for  game play pops up.

![gamePlayer UI](gamePlayerUI.png "Game Player UI")



## Design Details

### Game Engine

#### Phase class

This class contains a finite state machine that represents the actions and logic (see Node class, below) within each turn. It contains a start node and a terminal node, which will change the Player whose turn it is and will start a new Phase within the Turn class. It contains a "cursor" that will traverse the FSM in the traverse() method. 

#### Node class

This class represents an action that is requested from the player, such as a mouse click or key press. It has an EventHandler, which will be passed from the authoring environment, that contains a validity check and execution script that is performed in response to the user's input. If the input is valid, the next Node is visited; if not, it visits the startNode within the FSM. 

#### Player class

This class contains the id of the player, as well as any stats (like score, HP, etc.) associated with him/her. 

#### Sprite class
This class contains the id of the sprite, its location (in terms of which Cell it's on), which player it belongs to (if any), and any other related stats (like sprite HP, etc.). The logic of how this sprite is able to move is encoded within the edges of each Phase's FSM.  

#### Cell class
Cells makes up the game's grid, which is an abstract concept in our design. Each cell has an id and which player it belongs to (if any). The latter information is important in games like chess, where the location of the piece may change its functionality (such as when a pawn crosses to the opposing player's side). It also contains a set of Sprites that may be on top of it. 

#### Turn class

This class indicates the current phase, the current Player (whose turn it is), and any global data relevant to the game. Its method startPhase() calls the traverse method of the current Phase, which essentially starts the execution of game logic. If the win/lose condition is ever met (which are checked for during the validity checks within the Phase's FSM), then the Turn class's endGame() method is called to end the game. 

#### Initialization class

Based on the XML, this class initializes and displays the Cell, Sprites, Players, Phases (and their FSMs), and finally, the Turn class. This class then starts gameplay by calling startPhase() in the Turn class. 

---

#### Game Engine Class

The engine class controls the entire running of a game. It will have a complementary display that  renders the grid and player stats. 

#### Turn Class

The turn class is responsible for handling the execution of each sequential action that occurs in the game.

#### Win Class

The win class is queried by the Turn class and is responsible for checking whether the winning or losing conditions have been met. If so, the game is over. 

#### FiniteChoiceMachine Class

The finite choice machine class is used to control the sequential execution of actions and the next possible actions. This class accepts a Token and transverses a graph of choice nodes and directional edges. The class keeps track of the current node that the game is occupying. When a method is called on the class it receives a Token that corresponds to a value representing an edge. First, it gets the value stored in the current node and passes the value to a behavior class which handles the execution of the corresponding behavior. Then it receives a new set of tokens and polls the top value in the stack. The name of the token corresponds to one of the edges leading from the current node. The class follows the node to the linked node and the process repeats. 

This design allows the game author to define all possible sequences of choices in the game and dynamically choose which to follow based on the 

#### Token Class

The token class is responsible for handling the manipulation and calling of tokens used to transverse the FCM.

#### Behavior Class

This Behavior class indicates both the action associated with a specific node in the FCM and the appropriate Token that should be appended afterwards. The behvior is specified through groovy script injections defined in the authoring environment. This behavior is encapsulated in the rules class. After determining the user's command it passes the information off to the manipulator class to handle the actual execution of the command. 

#### Rules Class

This rules class encapsulates the behavior related to getting and checking of user defined actions. It allows the engine to retreive the scripts that the author wrote in groovy relating to a particular key. Sets of behaviors can be defined by the user and if during the running of the game this rule set needs to be changed a different rule class can be instantiated. 

#### Manipulator Class

The manipulator class is the class that handles the interaction between user defined logic and the state of the game. It listens to user clicks and other events with the gui and feeds these inputs into the scripts written by the game author. In order to accomplish this is checks the validity of the inputs and controlls what pieces of data the scripts can interact with.

#### Player Class

The Player class is responsible for consuming the Token and manipulating the shared model (like the grid in checkers). This player could also have certain variables (such as health points, coins collected, etc.) as part of its stats that will be displayed dynamically on the screen during gameplay. 

#### ModelSprite Class

The sprites class 

---
### Game Authoring

#### Grid Class

The Grid Class encapsulates the position and contains the tiles or sprites. It can hold either a list of lists or a map.

#### Tile Class

The tile class is responsible for storing the customization of the functionalities and the appearance of spaces in which the sprites reside. 

#### Sprite Class

The Sprite class is bound to each entity, and they hold the logic to multiplex the images depending on the entity's instance variables/global variables.

#### Stage Class

The Stage class encapsulates the fsm of user inputs that can happen under a stage. 

def initialCursor: Int 
def fsm: Map[Int, List[UserInput]]
 
#### UserInput Interface
User input serves as an edge for the fsm, with its side effects built via Block Code. It also has a "guard" that allows the fsm to check whether it's possible to move to "to" or not.

``` java
def to: Int
def guard: BlockCode 
def sideEffect: BlockCode
```

##### MouseClick (entity)

MouseClick class 

##### MouseDrag (fromEntity, toEntity)

##### KeyPressed (entity)



---
### Game Player

#### SaveState Class

This class is responsible for saving the current state of the game, whenever the user decides to navigate out of the game. It will keep the state of the Grid, current scores for each player, high scores, and all other stats relevant to the game. The XML generated from this game will be used by the engine in re-initialization. 

#### GameMenu Class
This class will display all the available games in a list with descriptions. Clicking on any of these will launch the games--if there are any saved states, the engine will run these, and if not, the game engine will reinitialize. 

---
### Game Data
Game data will be saved in the form of XML files. We will have a data folder with the images available to the author, and the filepaths of these images will be encoded in the XMLs for the game engine to access.

---
### Front-End 
- **View**
    - APIs
        - External
        - Internal
            - Data Interfaces
                - import data
                    - void importGame()
                    - void importEntities()
                    - void importPreferences()
                - update data
                    - void updateGame()
                    - void updateEntities()
                    - void importPreferences()
                - export data
                    - void exportGame()
                    - void updateEntities()
                    - void updatePreferences()
            
            - EditWindow
                - void updateEditView(): when the "apply" button is clicked
            - MenuBarView
                - void closeFile(ActionEvent event) 
                - void newFile(ActionEvent event) 
                - void openNewFile(ActionEvent event)
                - void redoChanges(ActionEvent event)
                - void runGame(ActionEvent event): when "run" button is clicked. Initialize GamePreView
                - void saveFile(ActionEvent event) 
                - void saveFileAs(ActionEvent event) 
                - void showAbout(ActionEvent event) 
                - void showHelpDoc(ActionEvent event) 
                - void undoChanges(ActionEvent event) 
            - SideView
                - **How to detect an object is dropped and update EditView
                    - **When there is an onDragStart event happening on the SideView, store that event somehow, and when there is a onDragExited event is detected, get information from the dragStart event and get the view from that place and put it here.
    - Classes that implement interface _SubView_ (contains method: Node getView())
        - `MenuBarView` (MenuBar containing all the buttons)
            - MenuBarView has one MenuBar JavaFx Node which has the following JavaFx MenuItem JavaFx Nodes.
            - File
                - New
                - Open
                - Save
                - Save as
            - Edit
                - Undo
                - Redo
            - Run
                - initialize a new GamePreView
            - Help
        - `SideView` (TreeView storing all the elements and supporting drag and drop)
                - Entity
                    - display instances of sprites
                - Sound
                    - store all the sound files
                - Tile Sets
                    - set shape of the grid and number of grid
            - Internal APIs
                - void addEntity(Entity);
                - void addSound(Sound);
                - void addTileSets(TileSets);
        - `EditView` (AnchorPane > TabPane > ScrollPane)
            - Representation of the game setting (e.g. entities, grids)
            - Support Zoom in and zoom out
            - Internal APIs
                - void handleZoom();
                
- **EditWindow** (an abstract class)
    - EntityEditWindow (edit the characters settings)
        - A Dialogue box (pop up window in the same stage) for user to add an Entity element in the storage
    - SoundEditWindow (edit the sound setting)
        - A Dialogue box (pop up window in the same stage) for user to add a sound element in the storage
    - TileSetsEditWindow (edit the tile setting)
        - A Dialogue box (pop up window in the same stage) for user to add a sound element in the storage
- **GamePreView** (A new stage with the game itself, dependent of the backend data to run)
    - Take in everything from the EditView, combine with user's settings
- **Utils**
    - Entity
        - Name
        - Image
            - Static Image Builder (loading images)
            - Draggable Image Builder (Extending Static Image Builder, e.g. mouse event handler, set X position, set Y position)
            - HBox image Holder (images will be wrapped by Hbox) 
        - Actions (Depend on backend, e.g. move forward, move backward)
        - Size (width and height dependent of the size of the grid)
    - TileSets
        - A map or a 2D array
    
## Example Games
1. **Tic Tac Toe.** This 2-player involves each player making a mark on the grid of their designated symbol in turns. The one who gets 3 of their marks in a row first, wins. According to to our current design, the FCM in this case would have 2 nodes: requesting user input from player 1 and doing the same for player 2. At each node, the designated player would interact with the Grid via the manipulation class, which would rerender it before the current Token is popped. 
2. **Uno.** This game involves players trying to get rid of all of their cards the fastest. Cards they may play are restricted to those in their hand, and these cards may make other players draw cards, skip turns, etc. The number of nodes within our FCM would be twice the number of players--this is because each player's turn is represented by a node, and these each have a node in between that represents drawing a card. This game, unlike Tic Tac Toe, demonstrates the benefit of using Tokens. Whenever a card is played (which, in our design, is translated as a card being placed on the Grid in a shared, designated space), either player data or the turn sequence (or both) will be manipulated. In the case of skipped turns, tokens will just be popped without execution, and in the case of added turns, tokens will be added.
3. **Chess.** This 2-player game involves movement of pieces across the board to place the opposing player in a checkmate, or a position where their king piece cannot move safely. According to our design, the user will be able to define (in the authoring environment) validity checks for each piece, which essentially encapsulate its movement behavior. This check is performed by the Manipulator class in the game engine, and it may have different if-else conditions depending on the game's nature. For example, when pawns cross the board and retain the functionality of a queen, its validity check condition is switched to enable different movements. At every turn, the Turn class will query the Win class to ensure that the king is of any player is not in checkmate. 

## Example code:

Pick at least 5 use cases and provide example “implementations” of them, using only methods from your code interfaces. Note, these should be separate Java code files in the folder doc/usecases that compile and contain extensive comments explaining how you expect your different APIs to collaborate. Note, you can implement each feature either in a single class to show how the steps are connected “procedurally” or in separate classes that implement the necessary interfaces to show how the steps are “distributed” across the objects. In either case, you will likely need to create simple “mocked up” objects that implement your interfaces so you have something concrete to create, using new, and call methods on.

1. The user runs the game for testing
    - src/frontend/src/authoringInterface/menu/MenuBarView.java
    ```java 
    public class GameWindow {
            Stage newWindow = new Stage();
            newWindow.setTitle("Your Game");
            Group group = new Group();
            Scene newScene = new Scene(group, GAME_WIDTH, GAME_HEIGHT);
            newWindow.setScene(newScene);
            newWindow.setX(MainAuthoringProgram.SCREEN_WIDTH*0.5 - GAME_WIDTH*0.5);
            newWindow.setY(MainAuthoringProgram.SCREEN_HEIGHT*0.5 - GAME_HEIGHT*0.5);
            newWindow.show();
            }

    ```

    - src/frontend/src/runningGame/GameWindow.java
    ```java
     void handleRunProject(ActionEvent event) {
         new GameWindow();
     }
    ```
2. The user adds an entity to be stored and re-used later
    - src/frontend/src/authoringInterface/spritechoosingwindow/**EntityWindow.java** -> provides a constructor for a new window to be displayed
    - src/frontend/src/authoringInterface/sidebar/SideView.java
        -         rootTreeView.setOnMouseClicked(event -> new EntityWindow(primaryStage));

3. The user can edit multiple games at the same time
    - src/frontend/src/authoringInterface/editor/EditView.java
    ```java
    private void addTab(){
        Tab tempTab = new Tab("Tab" + index);
        tempTab.setClosable(true);
        tempTab.setContent(gridScrollView.getView());
        tabPane.getTabs().add(tempTab);
        index++;
    }
    ```
    - Note, in the fourth line, `gridScrollView.getView()` needs to be changed. Each time when a new tab is created, a Controller is created

4. The user can attach 

## Example data:

Write any number of data files needed to represent one, simple, game. The goal of these example files (such as property files, XML files, and other resource files like HTML pages, images, or sounds) is to help the team agree to standard locations and formats for your shared data. Note, your example does not need to include all the data fields that will be needed for all objects in the game, just to be representative of the file’s format.

## Design Considerations

### Design Decision #1: Modular Design for Authoring Engine (front-end)

**Pros:** The different editing panels are modularized as much as possible. At each "level" of the editing interface, the user can add in any editing panels that implement the SubView interface as long as that level of the panel implements ParentView<T> interface. It allows easy composition of the editing windows both for us developers and for the user in the future.

**Cons:** It's easy to implement these two interfaces, but it is hard to organize thse window panels in Parent window. After the user decides to add an Entity edit window into the panel, it is hard for the parent to automatically decide where to position that Entity endit panel.

**Our Rationale** These windows may be different for different kinds of games, and we do not want to initialize windows in a hard-coded way.

### Design Decision #2: Having Phase-Specific FSMs

**Pros:** Having a FSM specific to each phase makes it easier to implement different rules/difficulty/goals for various levels of a game. A single FSM that represents all the game logic would also have nodes specific to each player's turn, which provides unnecessary complexity. 

**Cons:** As mentioned above, a single FSM would allow for everyone's turn to be represented in the graph. This would encapsulate the logic of reversing, skipping, and adding turns. 

**Our Rationale** We decided to use phase-specific FSMs because it is more encapsulated than a game-wise FSM. As mentioned, it also allows for the easy addition of different levels, and it also separates the logic of turns into a separate class.

### Design Decision #3: 