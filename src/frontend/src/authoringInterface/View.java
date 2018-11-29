package authoringInterface;

import api.DraggingCanvas;
import api.ParentView;
import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.editor.editView.EditView;
import authoringInterface.editor.menuBarView.EditorMenuBarView;
import authoringInterface.sidebar.SideView;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.crud.SimpleGameObjectsCRUD;
import graphUI.groovy.GroovyPaneFactory;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class provides an createGraph skeleton window with the basic menu items, and basic editing interfaces.
 *
 * @author Haotian Wang
 * @author jl729
 * @author Amy
 */
public class View implements ParentView<SubView>, DraggingCanvas {
    private AnchorPane rootPane;
    private EditorMenuBarView menuBar;
    private SideView sideView;
    private GroovyPaneFactory groovyPaneFactory;
    private EditView editView;
    private Stage primaryStage;
    private AuthoringTools tools;
    private Node preview;
    private GameObjectsCRUDInterface gameObjectManager;
    public static final double MENU_BAR_HEIGHT = 30;
    public static final double GAME_WIDTH = 700;
    public static final double GAME_HEIGHT = 500;
    private static final int ROW_NUMBER = 10;
    private static final int COL_NUMBER = 7;

    /**
     * Constructor for an createGraph window, with an AnchorPane as the root Node, and the AnchorPane constraints on top, left and right are 0.
     */
    public View(Stage primaryStage) {
        this.primaryStage = primaryStage;
        rootPane = new AnchorPane();
        tools = new AuthoringTools(COL_NUMBER, ROW_NUMBER);
        gameObjectManager = tools.entityDB();
        groovyPaneFactory = new GroovyPaneFactory(primaryStage, tools.factory());

        initializeElements();
        setElements();
        addElements();
        setupDraggingCanvas();
    }

    private void initializeElements() {
        menuBar = new EditorMenuBarView(tools, primaryStage::close, this::updateGridDimension);
        sideView = new SideView(gameObjectManager);
        editView = new EditView(tools, groovyPaneFactory, ROW_NUMBER, COL_NUMBER, gameObjectManager);
    }

    private void updateGridDimension(Integer width, Integer height) {
        tools.setGridDimension(width, height);
        editView.updateDimension(width, height);
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
                if (item == null || !item.isLeaf()) {
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
            }
        });
        rootPane.addEventFilter(MouseDragEvent.MOUSE_DRAG_OVER, e -> {
            if (preview == null) {
                return;
            }
            if (!rootPane.getChildren().contains(preview)) {
                rootPane.getChildren().add(preview);
            }
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
            if (preview == null) {
                return;
            }
            rootPane.getChildren().remove(preview);
            preview = null;
        });
    }
}
