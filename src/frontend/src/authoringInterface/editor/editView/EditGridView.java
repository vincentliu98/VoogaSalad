package authoringInterface.editor.editView;

import api.SubView;
import authoringInterface.customEvent.UpdateStatusEventListener;
import authoringInterface.subEditors.AbstractGameObjectEditor;
import authoringInterface.subEditors.EditorFactory;
import authoringInterface.subEditors.exception.MissingEditorForTypeException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import grids.Point;
import grids.PointImpl;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.exception.NodeNotFoundException;
import utils.exception.PreviewUnavailableException;
import utils.exception.UnremovableNodeException;
import utils.imageManipulation.ImageManager;
import utils.imageManipulation.JavaFxOperation;
import utils.nodeInstance.NodeInstanceController;
import utils.simpleAnimation.SingleNodeFade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * EditGridView Class (ScrollPane)
 *      - Representation of the game's grid setting
 *      - It should support Zoom in and zoom out
 *
 * @author Haotian Wang
 * @author Amy Kim
 */
public class EditGridView implements SubView<ScrollPane> {
    private GridPane gridScrollView;
    private ScrollPane scrollPane;
    private GameObjectsCRUDInterface gameObjectManager;
    private NodeInstanceController nodeInstanceController;
    private List<UpdateStatusEventListener<Node>> listeners;
    private static final double NODE_HEIGHT = 75;
    private static final double NODE_WIDTH = 75;
    private boolean isControlDown;
    private boolean isShiftDown;
    private Label batchMode;
    private Label deleteMode;
    private static final double INDICATOR_FADE_TIME = 3000;
    private static final double INITIAL_INDICATOR_FADE_TIME = 15000;
    private static final double CELL_HEIGHT = 100;
    private static final double CELL_WIDTH = 100;
    private Set<Node> toRemove;
    private static final double HALF_OPACITY = 0.5;
    private static final double FULL_OPACITY = 1;

    public EditGridView(int row, int col, GameObjectsCRUDInterface manager, NodeInstanceController controller) {
        gameObjectManager = manager;
        nodeInstanceController = controller;
        toRemove = new HashSet<>();
        scrollPane = new ScrollPane();
        listeners = new ArrayList<>();
        batchMode = new Label("Batch Mode: Off\nPress Shift to Toggle");
        batchMode.setFont(Font.font(20));
        batchMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        deleteMode = new Label("Delete Mode: Off\nPress Control to Toggle");
        deleteMode.setFont(Font.font(20));
        deleteMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        gridScrollView = new GridPane();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                StackPane cell = new StackPane();
                cell.setPrefWidth(CELL_WIDTH);
                cell.setPrefHeight(CELL_HEIGHT);
                gridScrollView.add(cell, j, i);
                cell.setOnDragOver(e -> setUpHoveringColorDraggedOver(e, Color.LIGHTGREEN, cell));
                cell.setOnDragExited(e -> setUpDragExit(e, cell));
                cell.setOnDragDropped(e -> handleDragFromSideView(e, cell));
                cell.setOnMouseClicked(e -> listeners.forEach(listener -> listener.setOnUpdateStatusEvent(constructStatusView(cell))));
                cell.setOnDragEntered(e -> setUpBatchInstanceDrag(e, cell));
            }
        }
        gridScrollView.setGridLinesVisible(true);
        gridScrollView.add(batchMode, 0, 0, 3, 2);
        gridScrollView.add(deleteMode, 0, 1, 3, 2);
        SingleNodeFade.getNodeFadeOut(batchMode, INITIAL_INDICATOR_FADE_TIME).playFromStart();
        SingleNodeFade.getNodeFadeOut(deleteMode, INITIAL_INDICATOR_FADE_TIME).playFromStart();
        scrollPane = new ScrollPane(gridScrollView);
        scrollPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.CONTROL) {
                setUpControl();
            } else if (keyEvent.getCode() == KeyCode.SHIFT) {
                setUpShift();
            } else if (keyEvent.getCode() == KeyCode.DELETE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                toRemove.forEach(this::handleNodeDeleting);
            }
        });

        // Fill grids with what we have
        manager.getAllInstances().forEach(instance -> {
            Point p = null;
            switch(instance.getType()) {
                case ENTITY:
                    p = ((EntityInstance) instance).getCoord();
                    break;
                case TILE:
                    p = ((TileInstance) instance).getCoord();
            }
            if(p == null) return;
            var cell = getCellAt(p.getY(), p.getX());
            if(cell == null) return;
            createInstanceAtGridCell(instance, cell);
        });
    }

    private Pane getCellAt(int r, int c) {
        for(var child : gridScrollView.getChildren()) {
            if(r == GridPane.getRowIndex(child) && c == GridPane.getColumnIndex(child)) {
                return (StackPane) child;
            }
        } return null;
    }

    /**
     * Set up a key toggle and attach the this boolean toggle to some boolean variable of this class.
     */
    private void setUpControl() {
        isControlDown = !isControlDown;
        if (isControlDown) {
            deleteMode.setText("Delete Mode: On");
            deleteMode.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
        } else {
            deleteMode.setText("Delete Mode: Off");
            deleteMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        }
        SingleNodeFade.getNodeFadeInAndOut(deleteMode, INDICATOR_FADE_TIME).playFromStart();
    }

    /**
     * Set up a key toggle for toggling for the Shift key.
     */
    private void setUpShift() {
        isShiftDown = !isShiftDown;
        if (isShiftDown) {
            batchMode.setText("Batch Mode: On");
            batchMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        } else {
            batchMode.setText("Batch Mode: Off");
            batchMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        }
        SingleNodeFade.getNodeFadeInAndOut(batchMode, INDICATOR_FADE_TIME).playFromStart();
    }

    public void updateDimension(int row, int col) {
        gridScrollView.getChildren().removeIf(c -> c instanceof StackPane);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                StackPane cell = new StackPane();
                cell.setPrefWidth(CELL_WIDTH);
                cell.setPrefHeight(CELL_HEIGHT);
                gridScrollView.add(cell, j, i);
                cell.setOnDragOver(e -> setUpHoveringColorDraggedOver(e, Color.LIGHTGREEN, cell));
                cell.setOnDragExited(e -> setUpDragExit(e, cell));
                cell.setOnDragDropped(e -> handleDragFromSideView(e, cell));
                cell.setOnMouseClicked(e -> listeners.forEach(listener -> listener.setOnUpdateStatusEvent(constructStatusView(cell))));
                cell.setOnDragEntered(e -> setUpBatchInstanceDrag(e, cell));
            }
        }
        gameObjectManager.setDimension(row, col);
        nodeInstanceController.clearAllLinks();
        try {
            gameObjectManager.deleteAllInstances();
        } catch (InvalidIdException e) {
            // TODO: error handling
            e.printStackTrace();
        } catch (GameObjectClassNotFoundException e) {
            // TODO: error handling
            e.printStackTrace();
        } catch (GameObjectTypeException e) {
            // TODO: error handling
            e.printStackTrace();
        }
    }

    /**
     * This method returns a GridPane listing as entries the GameObjectInstances together with their JavaFx Nodes to be shown in the UpdateStatusEventListener.
     *
     * @return GridPane: The GridPane that contains information about the GameObjectInstances and JavaFx nodes at this cell.
     */
    private GridPane constructStatusView(Region cell) {
        GridPane listView = new GridPane();
        listView.setGridLinesVisible(true);
        listView.addRow(0, new Label("ID"), new Label("Instance"), new Label("Class"));
        cell.getChildrenUnmodifiable().forEach(node -> {
            GameObjectInstance instance = null;
            try {
                instance = nodeInstanceController.getGameObjectInstance(node);
                Text instanceID = new Text(String.valueOf(instance.getInstanceId()));
                Text instanceName = new Text(instance.getInstanceName());
                Text className = new Text(instance.getClassName());
                Button edit = new Button("Edit");
                Button delete = new Button("Delete");
                GridPane control = new GridPane();
                control.addColumn(0, edit, delete);
                instanceID.setOnMouseClicked(e -> handleDoubleClick(e, node));
                instanceName.setOnMouseClicked(e -> handleDoubleClick(e, node));
                className.setOnMouseClicked(e -> handleDoubleClick(e, node));
                edit.setOnMouseClicked(e -> handleNodeEditing(node));
                delete.setOnMouseClicked(e -> handleNodeDeleting(node));
                listView.addRow(
                        listView.getRowCount(),
                        instanceID,
                        instanceName,
                        className,
                        control
                );
                listView.getChildrenUnmodifiable().forEach(node1 -> GridPane.setHgrow(node1, Priority.ALWAYS));

            } catch (NodeNotFoundException e) {
                // TODO: Proper error handling.
                e.printStackTrace();
            }
        });
        return listView;
    }

    /**
     * This method opens an editor window if the user wishes to double click on an object already dropped on the grid.
     *
     * @param event: A MouseEvent event, which is a double click.
     * @param targetNode: The JavaFx Node where this double click occurs.
     */
    private void handleDoubleClick(MouseEvent event, Node targetNode) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            handleNodeEditing(targetNode);
        }
    }

    /**
     * This method opens the respective editor depending on the GameObjectInstance Type. It also reads in the data from the existing GameObjectInstance.
     *
     * @param targetNode: A JavaFx Node that represents a GameObjectInstance.
     */
    private void handleNodeEditing(Node targetNode) {
        GameObjectInstance userObject = null;
        try {
            userObject = nodeInstanceController.getGameObjectInstance(targetNode);
        } catch (NodeNotFoundException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        AbstractGameObjectEditor editor = null;
        try {
            assert userObject != null;
            editor = EditorFactory.makeEditor(userObject.getType(), gameObjectManager);
        } catch (MissingEditorForTypeException e) {
            // TODO
            e.printStackTrace();
        }
        assert editor != null;
        editor.editNode(targetNode, nodeInstanceController);
        dialogStage.setScene(new Scene(editor.getView(), 600, 620));
        dialogStage.show();
    }

    /**
     * This method deletes the target node from grid and the whole editor.
     *
     * @param targetNode: A JavaFx Node that will be deleted.
     */
    private void handleNodeDeleting(Node targetNode) {
        try {
            JavaFxOperation.removeFromParent(targetNode);
        } catch (UnremovableNodeException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
        try {
            nodeInstanceController.removeNode(targetNode);
        } catch (NodeNotFoundException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
    }

    /**
     * Register the EditGridView with some listener to listen for StatusUpdateEvent changes.
     *
     * @param listener: A listener that listens for UpdateStatusEvents.
     */
    public void addUpdateStatusEventListener(UpdateStatusEventListener<Node> listener) {
        listeners.add(listener);
    }

    @Override
    public ScrollPane getView() {
        return scrollPane;
    }

    /**
     * This method accepts a Region as input and another Paint variable as input to set up a hovering coloring scheme. The region that is inputted will change to the defined color when hovered over.
     *
     * @param dragEvent: A DragEvent which should be DraggedOver
     * @param hoveringFill: The JavaFx Color scheme applied to the hovering.
     * @param cell: The Pane where the hovering occurs.
     */
    private void setUpHoveringColorDraggedOver(DragEvent dragEvent, Paint hoveringFill, Pane cell) {
        dragEvent.acceptTransferModes(TransferMode.ANY);
        if (!dragEvent.getDragboard().getString().isEmpty()) {
            cell.setBackground(new Background(new BackgroundFill(hoveringFill, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        dragEvent.consume();
    }

    /**
     * This method sets up a right click handler on a Node that allows the user to edit or delete this node.
     *
     * @param event: A MouseEvent that is RightClick.
     * @param node: A JavaFx Node where the right-click context menu will be set up.
     */
    private void setUpRightClickMenu(MouseEvent event, Node node) {
        if (event.getButton() != MouseButton.SECONDARY) {
            return;
        }
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit this instance");
        MenuItem delete = new MenuItem("Delete this instance");
        edit.setOnAction(e -> handleNodeEditing(node));
        delete.setOnAction(e -> handleNodeDeleting(node));
        contextMenu.getItems().addAll(edit, delete);
        contextMenu.show(node, event.getScreenX(), event.getScreenY());
    }

    /**
     * This method sets the Background of a cell back to empty once the hovering exits the cell.
     *
     * @param dragEvent: A DragEvent which should be DragExited.
     * @param cell: The Pane where the hovering exits.
     */
    private void setUpDragExit(DragEvent dragEvent, Pane cell) {
        if (!dragEvent.getDragboard().getString().isEmpty()) {
            cell.setBackground(Background.EMPTY);
        }
        dragEvent.consume();
    }

    /**
     * Create an instance at a specific Grid cell, which is a Pane from a GameObjectClass
     *
     * @param gameObjectInstance: A GameObjectInstance that is created on the grid.
     * @param cell: The Pane where an instance will be created.
     */
    private void createInstanceAtGridCell(GameObjectInstance gameObjectInstance, Pane cell) {
        ImageView nodeOnGrid = null;
        try {
            nodeOnGrid = new ImageView(ImageManager.getPreview(gameObjectInstance));
        } catch (PreviewUnavailableException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
        // TODO: smarter resizing
        assert nodeOnGrid != null;
        nodeOnGrid.setFitHeight(NODE_HEIGHT);
        nodeOnGrid.setFitWidth(NODE_WIDTH);
        ImageView finalNodeOnGrid = nodeOnGrid;
        cell.getChildren().add(finalNodeOnGrid);
        nodeInstanceController.addLink(finalNodeOnGrid, gameObjectInstance);
        nodeOnGrid.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                setUpRightClickMenu(e, finalNodeOnGrid);
            } else if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 && isControlDown) {
                handleClickToRemove(finalNodeOnGrid);
            } else {
                handleDoubleClick(e, finalNodeOnGrid);
            }
        });
        finalNodeOnGrid.setOnDragDetected(e -> {
            Dragboard db = finalNodeOnGrid.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cc = new ClipboardContent();
            cc.putString(gameObjectInstance.getInstanceName());
            db.setContent(cc);
            db.setDragView(finalNodeOnGrid.getImage());
        });
        int height = 0;
        int width = 0;
        if (gameObjectInstance.getType() == GameObjectType.ENTITY) {
            height = ((EntityInstance) gameObjectInstance).getHeight();
            width = ((EntityInstance) gameObjectInstance).getWidth();
        } else if (gameObjectInstance.getType() == GameObjectType.TILE) {
            height = ((TileInstance) gameObjectInstance).getHeight();
            width = ((TileInstance) gameObjectInstance).getWidth();
        }
        if (height != 0 && width != 0) {
            Tooltip.install(finalNodeOnGrid, new Tooltip(String.format("Width: %s\nHeight: %s\nSingle Click to toggle Deletion\nDouble Click or Right Click to edit\nInstance ID: %s\nClass Name: %s", width, height, gameObjectInstance.getInstanceId(), gameObjectInstance.getClassName())));
        }
    }

    private void createInstanceAtGridCell(GameObjectClass gameObjectClass, Pane cell) {
        GameObjectInstance gameObjectInstance = null;
        try {
            gameObjectInstance = gameObjectManager.createGameObjectInstance(gameObjectClass, new PointImpl(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)));
        } catch (GameObjectTypeException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
        createInstanceAtGridCell(gameObjectInstance, cell);
    }

    /**
     * This method handles the single click to toggle the to remove the status of the specified node.
     *
     * @param node: The JavaFx node whose status will be toggled.
     */
    private void handleClickToRemove(Node node) {
        if (!toRemove.remove(node)) {
            node.setOpacity(HALF_OPACITY);
            toRemove.add(node);
        } else {
            node.setOpacity(FULL_OPACITY);
        }
    }

    /**
     * This method sets up a region so that it accepts a MouseDragEvent Released event from the sideview. The Release event will create an instance according to the GameObjectClass from which the drag is initiated.
     *
     * @param dragEvent: A DragEvent that should be DragDropped.
     * @param cell: The region where the event handler will be set up.
     */
    private void handleDragFromSideView(DragEvent dragEvent, Pane cell) {
        if (dragEvent.getGestureSource() instanceof TreeCell) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
            GameObjectClass objectClass = null;
            try {
                objectClass = gameObjectManager.getGameObjectClass(dragEvent.getDragboard().getString());
            } catch (GameObjectClassNotFoundException e) {
                // TODO
                e.printStackTrace();
            }
            createInstanceAtGridCell(objectClass, cell);
        }
        dragEvent.consume();
    }

    /**
     * This method creates many sequences on the Grid if the user drag a GameObjectClass while Shift is being pressed down.
     *
     * @param dragEvent: A DragEvent that should be DragEntered.
     * @param cell: A Pane where the GameObjectInstance will be created.
     */
    private void setUpBatchInstanceDrag(DragEvent dragEvent, Pane cell) {
        if (isShiftDown) {
            handleDragFromSideView(dragEvent, cell);
        }
    }
}
