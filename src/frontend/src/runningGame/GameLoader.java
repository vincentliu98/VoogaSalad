package runningGame;

import javafx.scene.Node;

import java.io.File;

/**
 * This class loads a saved XML file and the relevant data files to resume a game. It has methods to save an existing game to a target directory.
 *
 * @author Haotian Wang
 */
public class GameLoader {
    private Node savedGame;

    public GameLoader() {

    }

    /**
     * This method handles the saving of game progress.
     */
    public void saveGame() {
    }

    /**
     * This method loads pre-saved gaming files.
     *
     * @param file: A saved game in the appropriate format.
     */
    void loadGame(File file) throws IllegalSavedGameException {
    }

    /**
     * This method returns the Node representing a previously saved game.
     *
     * @return A JavaFx node representing a game.
     */
    Node getSavedGame() {
        return null;
    }
}
