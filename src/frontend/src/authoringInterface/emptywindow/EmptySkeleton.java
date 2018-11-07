package authoringInterface.emptywindow;

import api.SubView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;


/**
 * This class provides an empty skeleton window with the basic menu items.
 *
 * @author  Haotian Wang
 */
public class EmptySkeleton implements SubView {
    private AnchorPane rootPane;

    /**
     * Constructor for an empty window, with an AnchorPane as the root Node, and the AnchorPane constraints on top, left and right are 0.
     */
    public EmptySkeleton() {
        rootPane = new AnchorPane();
        MenuBar menuBar = (MenuBar) new MenuBarView().getView();
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);
        AnchorPane.setTopAnchor(menuBar, 0.0);
        rootPane.getChildren().add(menuBar);
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public Node getView() {
        return rootPane;
    }
}
