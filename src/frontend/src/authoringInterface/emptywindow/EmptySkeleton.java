package authoringInterface.emptywindow;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
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

        TabPane tabView = (TabPane) new EditView().getView();
        AnchorPane.setLeftAnchor(tabView, 0.0);
        AnchorPane.setRightAnchor(tabView, 250.0);
        AnchorPane.setTopAnchor(tabView, 30.0);
        AnchorPane.setBottomAnchor(tabView, 0.0);
        rootPane.getChildren().add(tabView);

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
