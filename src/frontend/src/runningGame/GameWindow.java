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
    private static final double MEUNU_BAR_HEIGHT = 30;

    private AnchorPane rootPane;
    private GameMenuBarView menuBarView;

    public GameWindow() {
        rootPane = new AnchorPane();
        menuBarView = new GameMenuBarView(30);
    }

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
