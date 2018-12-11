package runningGame;

import api.SubView;
import javafx.scene.layout.AnchorPane;

/**
 * A new window for running the game.
 * <p>
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
        menuBarView = new GameMenuBarView(MEUNU_BAR_HEIGHT);
        initializeAnchorPane();
    }

    /**
     * Initialize the different positions of the components in a gaming window.
     */
    private void initializeAnchorPane() {
        rootPane.getChildren().add(menuBarView.getView());
        AnchorPane.setLeftAnchor(menuBarView.getView(), 0.0);
        AnchorPane.setTopAnchor(menuBarView.getView(), 0.0);
        AnchorPane.setRightAnchor(menuBarView.getView(), 0.0);
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
