package authoringInterface;

import api.SubView;
import authoringInterface.subviews.MenuBarView;
import authoringInterface.subviews.SideView;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


/**
 * This class provides an empty skeleton window with the basic menu items.
 *
 * @author  Haotian Wang
 */
public class EmptySkeleton implements SubView {
    private AnchorPane rootPane;
    private MenuBarView menuBar;
    private SideView sideView;


    /**
     * Constructor for an empty window, with an AnchorPane as the root Node, and the AnchorPane constraints on top, left and right are 0.
     */
    public EmptySkeleton() {
        rootPane = new AnchorPane();
        initializeElements();
        setElements();
        addElements();
    }

    private void initializeElements() {
        menuBar = new MenuBarView();
        sideView = new SideView();
    }

    private void setElements() {
        AnchorPane.setLeftAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(menuBar.getView(), 0.0);
        AnchorPane.setTopAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(sideView.getView(), 0.0);
        AnchorPane.setTopAnchor(sideView.getView(), 30.0);
        AnchorPane.setBottomAnchor(sideView.getView(), 0.0);
    }

    private void addElements() {
        rootPane.getChildren().addAll(menuBar.getView(), sideView.getView());
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
