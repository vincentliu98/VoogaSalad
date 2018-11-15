package runningGame;

import api.SubView;
import authoringInterface.editor.EditorMenuBarView;
import javafx.scene.layout.AnchorPane;

/**
 * A new window for running the game.
 *
 * Takes in data from back end.
 *
 * @author jl729
 * @author Haotian Wang
 */

public class GameWindow implements SubView<AnchorPane> {
    private AnchorPane rootPane;
    private GameMenuBarView menuBarView;

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return rootPane;
    }
}
