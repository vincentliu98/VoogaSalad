package authoringInterface;

import api.DraggingCanvas;
import api.ParentView;
import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.editor.editView.EditView;
import authoringInterface.editor.menuBarView.EditorMenuBarView;
import authoringInterface.sidebar.SideView;
import authoringInterface.sidebar.StatusView;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.crud.SimpleGameObjectsCRUD;
import graphUI.groovy.GroovyPaneFactory;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.CrappyNodeInstanceController;
import utils.NodeInstanceController;

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
    private StatusView statusView;
    private GridPane sidebar;
    private NodeInstanceController nodeInstanceController;
    private GameObjectsCRUDInterface gameObjectManager;
    public static final double MENU_BAR_HEIGHT = 30;
    public static final double GAME_WIDTH = 700;
    public static final double GAME_HEIGHT = 500;
    private static final double SIDEBAR_WIDTH = 247;
    private static final int ROW_NUMBER = 10;
    private static final int COL_NUMBER = 7;

    /**
     * Constructor for an createGraph window, with an AnchorPane as the root Node, and the AnchorPane constraints on top, left and right are 0.
     */
    public View(Stage primaryStage) {
        this.primaryStage = primaryStage;
        rootPane = new AnchorPane();
        rootPane.getStyleClass().add("mainPane");
        tools = new AuthoringTools(COL_NUMBER, ROW_NUMBER);
        gameObjectManager = tools.entityDB();
        groovyPaneFactory = new GroovyPaneFactory(primaryStage, tools.factory());
        nodeInstanceController = new CrappyNodeInstanceController();
        initializeElements();
        setElements();
        addElements();
        setupDraggingCanvas();
    }

    private void initializeElements() {
        sidebar = new GridPane();
        menuBar = new EditorMenuBarView(tools, primaryStage::close, this::updateGridDimension);
        sideView = new SideView(gameObjectManager);
        editView = new EditView(tools, groovyPaneFactory, ROW_NUMBER, COL_NUMBER, gameObjectManager, nodeInstanceController);
        statusView = new StatusView(gameObjectManager);
        editView.addUpdateStatusEventListener(statusView);
        sidebar.addColumn(0, sideView.getView(), statusView.getView());
    }

    private void updateGridDimension(Integer width, Integer height) {
        tools.setGridDimension(width, height);
        editView.updateDimension(width, height);
    }

    /**
     * Set the positions of the components in an AnchorPane.
     */
    private void setElements() {
        AnchorPane.setLeftAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(menuBar.getView(), 0.0);
        AnchorPane.setTopAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(sidebar, 0.0);
        AnchorPane.setTopAnchor(sidebar, MENU_BAR_HEIGHT);
        AnchorPane.setBottomAnchor(sidebar, 0.0);
        AnchorPane.setLeftAnchor(editView.getView(), 0.0);
        AnchorPane.setRightAnchor(editView.getView(), SIDEBAR_WIDTH);
        AnchorPane.setTopAnchor(editView.getView(), MENU_BAR_HEIGHT);
        AnchorPane.setBottomAnchor(editView.getView(), 0.0);

    }

    private void addElements() {
        rootPane.getChildren().addAll(menuBar.getView(), editView.getView(), sidebar);
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
                //noinspection unchecked
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
        rootPane.addEventFilter(DragEvent.DRAG_OVER, e -> {
            System.out.println(e.toString());
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

        rootPane.setOnDragDropped(e -> {
            if (preview == null) {
                return;
            }
            rootPane.getChildren().remove(preview);
            preview = null;
        });
    }
}
