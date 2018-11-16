package authoringInterface;

import api.DraggingCanvas;
import api.ParentView;
import api.SubView;
import authoringInterface.editor.EditView;
import authoringInterface.editor.EditorMenuBarView;
import authoringInterface.sidebar.SideView;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import phase.api.GameEvent;

/**
 * This class provides an createGraph skeleton window with the basic menu items, and basic editing interfaces.
 *
 * @author  Haotian Wang
 * @author jl729
 */
public class View implements ParentView<SubView>, DraggingCanvas {
    private AnchorPane rootPane;
    private EditorMenuBarView menuBar;
    private SideView sideView;
    private EditView editView;
    private Stage primaryStage;
    private Node preview;
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
        setupDraggingCanvas();
    }

    private void initializeElements() {
        menuBar = new EditorMenuBarView();
        sideView = new SideView(primaryStage);
        editView = new EditView();

    }

    private void setElements() {
        AnchorPane.setLeftAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(menuBar.getView(), 0.0);
        AnchorPane.setTopAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(sideView.getView(), 0.0);
        AnchorPane.setTopAnchor(sideView.getView(), MENU_BAR_HEIGHT);
        AnchorPane.setBottomAnchor(sideView.getView(), 0.0);
        AnchorPane.setLeftAnchor(editView.getView(), 0.0);
        AnchorPane.setRightAnchor(editView.getView(), 247.9);
        AnchorPane.setTopAnchor(editView.getView(), MENU_BAR_HEIGHT);
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

    /**
     * Setup the dragging canvas event filters.
     */
    @Override
    public void setupDraggingCanvas() {
        rootPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (e.getTarget() instanceof TreeCell) {
                TreeItem<String> item = (TreeItem<String>) ((TreeCell) e.getTarget()).getTreeItem();
                if (item == null || item.getChildren().size() != 0) {
                    return;
                }
                if (item.getGraphic() != null) {
                    ImageView graphic = (ImageView) item.getGraphic();
                    preview = new ImageView(graphic.getImage());
                    preview.setOpacity(0.5);
                    ((ImageView) preview).setX(e.getX());
                    ((ImageView) preview).setY(e.getY());
                    preview.setMouseTransparent(true);
                } else {
                    preview = new Text(item.getValue());
                    ((Text) preview).setX(e.getX());
                    ((Text) preview).setY(e.getY());
                    preview.setMouseTransparent(true);
                }
                rootPane.getChildren().add(preview);
            }
        });
        rootPane.addEventFilter(MouseDragEvent.MOUSE_DRAG_OVER, e -> {
            preview.setMouseTransparent(true);
            if (preview != null) {
                if (preview instanceof ImageView) {
                    ((ImageView) preview).setX(e.getX());
                    ((ImageView) preview).setY(e.getY());
                } else if (preview instanceof Text) {
                    ((Text) preview).setX(e.getX());
                    ((Text) preview).setY(e.getY());
                }
            }
        });

        rootPane.addEventFilter(MouseDragEvent.MOUSE_DRAG_RELEASED, e -> {
            preview.setMouseTransparent(true);
            rootPane.getChildren().remove(preview);
        });
    }
}
