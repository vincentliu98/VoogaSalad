package authoringInterface.editor.editView;

import api.SubView;
import authoringInterface.customEvent.UpdateStatusEventListener;
import authoringInterface.subEditors.*;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidGameObjectInstanceException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import grids.PointImpl;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.exception.PreviewUnavailableException;
import utils.imageManipulation.ImageManager;
import utils.nodeInstance.NodeInstanceController;
import utils.exception.NodeNotFoundException;
import utils.simpleAnimation.SingleNodeFade;

import java.util.ArrayList;
import java.util.List;

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
    private static final double INDICATOR_FADE_TIME = 3000;

    public EditGridView(int row, int col, GameObjectsCRUDInterface manager, NodeInstanceController controller) {
        gameObjectManager = manager;
        nodeInstanceController = controller;
        scrollPane = new ScrollPane();
        listeners = new ArrayList<>();
        batchMode = new Label("Batch Mode: Off\nPress Shift to Toggle");
        batchMode.setFont(Font.font(20));
        batchMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        gridScrollView = new GridPane();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                StackPane cell = new StackPane();
                cell.setPrefWidth(100);
                cell.setPrefHeight(100);
                gridScrollView.add(cell, i, j);
                cell.setOnDragOver(e -> setUpHoveringColorDraggedOver(e, Color.LIGHTGREEN, cell));
                cell.setOnDragExited(e -> setUpDragExit(e, cell));
                cell.setOnDragDropped(e -> handleDragFromSideView(e, cell));
                cell.setOnMouseClicked(e -> listeners.forEach(listener -> listener.setOnUpdateStatusEvent(constructStatusView(cell))));
                cell.setOnDragEntered(e -> setUpBatchInstanceDrag(e, cell));
            }
        }
        gridScrollView.setGridLinesVisible(true);
        gridScrollView.add(batchMode, 0, 0, 3, 2);
        SingleNodeFade.getNodeFadeOut(batchMode, 10000).playFromStart();
        scrollPane = new ScrollPane(gridScrollView);
        scrollPane.addEventFilter(KeyEvent.KEY_PRESSED, this::setUpControl);
        scrollPane.addEventFilter(KeyEvent.KEY_PRESSED, this::setUpShift);
    }

    /**
     * Set up a key toggle and attach the this boolean toggle to some boolean variable of this class.
     *
     * @param keyEvent: The KeyEvent that encodes the pressed key.
     */
    private void setUpControl(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL) {
            isControlDown = !isControlDown;
        }
    }

    /**
     * Set up a key toggle for toggling for the Shift key.
     *
     * @param keyEvent: The KeyEvent that encodes the pressed key.
     */
    private void setUpShift(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SHIFT) {
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
    }

    public void updateDimension(int width, int height) {
        gridScrollView.getChildren().removeIf(c -> c instanceof StackPane);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                StackPane cell = new StackPane();
                cell.setPrefWidth(100);
                cell.setPrefHeight(100);
                gridScrollView.add(cell, i, j);
                cell.setOnDragOver(e -> setUpHoveringColorDraggedOver(e, Color.LIGHTGREEN, cell));
                cell.setOnDragExited(e -> setUpDragExit(e, cell));
                cell.setOnDragDropped(e -> handleDragFromSideView(e, cell));
                cell.setOnMouseClicked(e -> listeners.forEach(listener -> listener.setOnUpdateStatusEvent(constructStatusView(cell))));
                cell.setOnDragEntered(e -> setUpBatchInstanceDrag(e, cell));
            }
        }
        nodeInstanceController.clearAllLinks();
        gameObjectManager.deleteAllInstances();
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
            } catch (NodeNotFoundException e) {
                // TODO: Proper error handling.
                e.printStackTrace();
            }
            Text instanceID = new Text(instance.getInstanceId().getValue().toString());
            Text instanceName = new Text(instance.getInstanceName().getValue());
            Text className = new Text(instance.getClassName().getValue());
            Button edit = new Button("Edit");
            instanceID.setOnMouseClicked(e -> handleDoubleClick(e, node));
            instanceName.setOnMouseClicked(e -> handleDoubleClick(e, node));
            className.setOnMouseClicked(e -> handleDoubleClick(e, node));
            edit.setOnMouseClicked(e -> handleNodeEditing(node));
            listView.addRow(
                    listView.getRowCount(),
                    instanceID,
                    instanceName,
                    className,
                    edit
            );
            listView.getChildrenUnmodifiable().forEach(node1 -> GridPane.setHgrow(node1, Priority.ALWAYS));
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
            editor = EditorFactory.makeEditor(userObject.getType(), gameObjectManager);
        } catch (MissingEditorForTypeException e) {
            // TODO
            e.printStackTrace();
        }
        editor.editNode(targetNode, nodeInstanceController);
        dialogStage.setScene(new Scene(editor.getView(), 500, 500));
        dialogStage.show();
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
        if (dragEvent.getGestureSource() instanceof TreeCell) {
            cell.setBackground(new Background(new BackgroundFill(hoveringFill, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        dragEvent.consume();
    }

    /**
     * This method sets the Background of a cell back to empty once the hovering exits the cell.
     *
     * @param dragEvent: A DragEvent which should be DragExited.
     * @param cell: The Pane where the hovering exits.
     */
    private void setUpDragExit(DragEvent dragEvent, Pane cell) {
        if (dragEvent.getGestureSource() instanceof TreeCell) {
            cell.setBackground(Background.EMPTY);
        }
        dragEvent.consume();
    }

    /**
     * Create an instance at a specific Grid cell, which is a Pane from a GameObjectClass
     *
     * @param gameObjectClass: A GameObjectClass whose instances will be created on the grid.
     * @param cell: The Pane where an instance will be created.
     */
    private void createInstanceAtGridCell(GameObjectClass gameObjectClass, Pane cell) {
        ImageView nodeOnGrid = null;
        try {
            nodeOnGrid = new ImageView(ImageManager.getPreview(gameObjectClass));
        } catch (PreviewUnavailableException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
        // TODO: smarter resizing
        nodeOnGrid.setFitHeight(NODE_HEIGHT);
        nodeOnGrid.setFitWidth(NODE_WIDTH);
        ImageView finalNodeOnGrid = nodeOnGrid;
        finalNodeOnGrid.setOnMouseClicked(e -> handleDoubleClick(e, finalNodeOnGrid));
        cell.getChildren().add(finalNodeOnGrid);
        GameObjectInstance gameObjectInstance = null;
        try {
            gameObjectInstance = gameObjectManager.createGameObjectInstance(gameObjectClass, gameObjectManager.getDefaultPlayerID(), new PointImpl(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)));
        } catch (GameObjectTypeException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
        nodeInstanceController.addLink(finalNodeOnGrid, gameObjectInstance);
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
