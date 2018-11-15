package authoringInterface;

import api.ParentView;
import api.SubView;
import authoringInterface.editor.EditView;
import authoringInterface.editor.MenuBarView;
import authoringInterface.sidebar.DraggableTreeItem;
import authoringInterface.sidebar.SideView;
import authoringInterface.sidebar.TreeItemType;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class provides an createGraph skeleton window with the basic menu items, and basic editing interfaces.
 *
 * @author  Haotian Wang
 * @author jl729
 */
public class View implements ParentView<SubView> {
    private AnchorPane rootPane;
    private MenuBarView menuBar;
    private SideView sideView;
    private EditView editView;
    private Stage primaryStage;
    public static final double MENU_BAR_HEIGHT = 30;
    public static final double GAME_WIDTH = 700;
    public static final double GAME_HEIGHT = 500;

    /**
     * Constructor for an createGraph window, with an AnchorPane as the root Node, and the AnchorPane constraints on top, left and right are 0.
     */
    public View(Stage primaryStage) {
        this.primaryStage = primaryStage;
        rootPane = new AnchorPane();
        initializeElements();
        setElements();
        addElements();
    }

    private void initializeElements() {
        menuBar = new MenuBarView();
        sideView = new SideView(primaryStage);
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
     * Add the JavaFx Node representation of a subView into the parent View in a hierarchical manner.
     *
     * @param view: A SubView object.
     */
    @Override
    public void addChild(SubView view) {
        rootPane.getChildren().add(view.getView());
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }
}
