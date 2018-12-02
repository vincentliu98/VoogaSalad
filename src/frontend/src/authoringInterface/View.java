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
import gameObjects.entity.EntityClass;
import gameObjects.gameObject.GameObjectClass;
import graphUI.groovy.GroovyPaneFactory;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Coordinates;
import utils.CrappyNodeInstanceController;
import utils.NodeInstanceController;
import utils.UnhandledCoordinatesClassException;

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
    private static final double PREVIEW_OPACITY = 0.5;

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
     * This method handles the dragging preview when the user drags some TreeItem somewhere.
     *
     * @param e: A DragEvent which is a DragOver.
     */
    private void handleMouseDragged(DragEvent e) {
        if (preview != null) {
            try {
                Coordinates.setXAndY(preview, e.getX(), e.getY());
            } catch (UnhandledCoordinatesClassException e1) {
                // TODO: proper error handling
                e1.printStackTrace();
            }
            return;
        }
        e.acceptTransferModes(TransferMode.ANY);
        GameObjectClass draggedClass = gameObjectManager.getGameObjectClass(e.getDragboard().getString());
        switch (draggedClass.getType()) {
            case ENTITY:
                ObservableList<String> imagePaths = ((EntityClass) draggedClass).getImagePathList();
                if (imagePaths == null || imagePaths.isEmpty()) {
                    preview = new Text(draggedClass.getClassName().getValue());
                    preview.setOpacity(PREVIEW_OPACITY);
                } else {
                    preview = new ImageView(imagePaths.get(0));
                    preview.setOpacity(PREVIEW_OPACITY);
                }
        }
        try {
            Coordinates.setXAndY(preview, e.getX(), e.getY());
        } catch (UnhandledCoordinatesClassException e1) {
            // TODO: proper error handling
            e1.printStackTrace();
        }
        rootPane.getChildren().add(preview);
    }

    /**
     * This method handles the removal of preview once the drag is completed.
     *
     * @param dragEvent: a DragEvent which should be DragDropped.
     */
    private void handleDragFinished(DragEvent dragEvent) {
        rootPane.getChildren().remove(preview);
        preview = null;
    }

    /**
     * Setup the dragging canvas event filters.
     */
    @Override
    public void setupDraggingCanvas() {
        rootPane.setOnDragOver(this::handleMouseDragged);
        rootPane.setOnDragDone(this::handleDragFinished);
    }
}
