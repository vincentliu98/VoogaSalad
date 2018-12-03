package authoringInterface.editor.editView;

import api.SubView;
import authoringInterface.customEvent.UpdateStatusEventListener;
import authoringInterface.subEditors.*;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import grids.PointImpl;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.exception.PreviewUnavailableException;
import utils.imageManipulation.Coordinates;
import utils.imageManipulation.ImageManager;
import utils.nodeInstance.NodeInstanceController;
import utils.exception.NodeNotFoundException;

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

    public EditGridView(int row, int col, GameObjectsCRUDInterface manager, NodeInstanceController controller) {
        gameObjectManager = manager;
        nodeInstanceController = controller;
        scrollPane = new ScrollPane();
        listeners = new ArrayList<>();
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
            }
        }
        gridScrollView.setGridLinesVisible(true);
        scrollPane = new ScrollPane(gridScrollView);
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
        listView.addRow(0, new Label("Instance ID"), new Label("Instance Name"), new Label("Class Name"));
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
            instanceID.setOnMouseClicked(e -> handleDoubleClick(e, node));
            instanceName.setOnMouseClicked(e -> handleDoubleClick(e, node));
            className.setOnMouseClicked(e -> handleDoubleClick(e, node));
            listView.addRow(
                    listView.getRowCount(),
                    instanceID,
                    instanceName,
                    className
            );
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
            GameObjectInstance userObject = null;
            try {
                userObject = nodeInstanceController.getGameObjectInstance(targetNode);
            } catch (NodeNotFoundException e) {
                // TODO: proper error handling
                e.printStackTrace();
            }
            GameObjectType type = userObject.getType();
            Stage dialogStage = new Stage();
            AbstractGameObjectEditor editor = null;
            switch (type) {
                case ENTITY:
                    editor = new EntityEditor(gameObjectManager);
                    break;
                case SOUND:
                    editor = new SoundEditor(gameObjectManager);
                    break;
                case TILE:
                    editor = new TileEditor(gameObjectManager);
                    break;
                case CATEGORY:
                    editor = new CategoryEditor(gameObjectManager);
                    break;
            }
            editor.editNode(targetNode, nodeInstanceController);
            dialogStage.setScene(new Scene(editor.getView(), 500, 500));
            dialogStage.show();
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
     * This method sets up a region so that it accepts a MouseDragEvent Released event from the sideview. The Release event will create an instance according to the GameObjectClass from which the drag is initiated.
     *
     * @param dragEvent: A DragEvent that should be DragDropped.
     * @param cell: The region where the event handler will be set up.
     */
    private void handleDragFromSideView(DragEvent dragEvent, Pane cell) {
        if (dragEvent.getGestureSource() instanceof TreeCell) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
            GameObjectClass objectClass = gameObjectManager.getGameObjectClass(dragEvent.getDragboard().getString());
            ImageView nodeOnGrid = null;
            try {
                nodeOnGrid = new ImageView(ImageManager.getPreview(objectClass));
            } catch (PreviewUnavailableException e) {
                // TODO: proper error handling
                e.printStackTrace();
            }
            // TODO: smarter resizing
            nodeOnGrid.setFitHeight(NODE_HEIGHT);
            nodeOnGrid.setFitWidth(NODE_WIDTH);
            ImageView finalNodeOnGrid = nodeOnGrid;
            nodeOnGrid.setOnMouseClicked(e -> handleDoubleClick(e, finalNodeOnGrid));
            cell.getChildren().add(nodeOnGrid);
            switch (objectClass.getType()) {
                case ENTITY:
                    // TODO: solve the TileID thing, and player ID thing
                    EntityInstance entityInstance = ((EntityClass) objectClass).createInstance(0, gameObjectManager.getDefaultPlayerID());
                    nodeInstanceController.addLink(nodeOnGrid, entityInstance);
                    break;
                case SOUND:
                    // TODO
                    break;
                case TILE:
                    // TODO: solve point
                    TileInstance tileInstance = ((TileClass) objectClass).createInstance(new PointImpl(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)));
                    nodeInstanceController.addLink(nodeOnGrid, tileInstance);
                    break;
            }
        }
    }
}
