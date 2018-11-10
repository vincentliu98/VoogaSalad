package authoringInterface.editor;

import api.ParentView;
import api.SubView;
import authoringInterface.menu.MenuBarView;
import authoringInterface.sidebar.SideView;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


/**
 * This class provides an empty skeleton window with the basic menu items, and basic editing interfaces.
 *
 * @author  Haotian Wang
 */
public class EmptySkeleton implements SubView<AnchorPane>, ParentView<SubView> {
    private AnchorPane rootPane;
    private MenuBarView menuBar;
    private SideView sideView;
    private EditView editView;


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
        editView = new EditView();

    }

    private void setElements() {
        AnchorPane.setLeftAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(menuBar.getView(), 0.0);
        AnchorPane.setTopAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(sideView.getView(), 0.0);
        AnchorPane.setTopAnchor(sideView.getView(), 30.0);
        AnchorPane.setBottomAnchor(sideView.getView(), 0.0);
        AnchorPane.setLeftAnchor(editView.getView(), 0.0);
        AnchorPane.setRightAnchor(editView.getView(), 247.9);
        AnchorPane.setTopAnchor(editView.getView(), 30.0);
        AnchorPane.setBottomAnchor(editView.getView(), 0.0);
    }

    private void addElements() {
        rootPane.getChildren().addAll(menuBar.getView(), sideView.getView(), editView.getView());
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

    /**
     * Add the JavaFx Node representation of a subView into the parent View in a hierarchical manner.
     *
     * @param view: A SubView object.
     */
    @Override
    public void addChild(SubView view) {
        rootPane.getChildren().add(view.getView());
    }
}
