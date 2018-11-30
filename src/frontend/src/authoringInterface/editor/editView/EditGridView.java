package authoringInterface.editor.editView;

import api.DraggingCanvas;
import api.SubView;
import authoringInterface.customEvent.UpdateStatusEventListener;
import authoringInterface.subEditors.*;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<Node, GameObjectInstance> nodeToGameObjectInstanceMap;
    private List<UpdateStatusEventListener<Node>> listeners = new ArrayList<>();

    public EditGridView(int row, int col, GameObjectsCRUDInterface manager) {
        gameObjectManager = manager;
        scrollPane = new ScrollPane();
        nodeToGameObjectInstanceMap = new HashMap<>();
        gridScrollView = new GridPane();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                StackPane cell = new StackPane();
                cell.setPrefWidth(100);
                cell.setPrefHeight(100);
                gridScrollView.add(cell, i, j);
                setupHoveringColorChange(cell, Color.LIGHTGREEN);
                receiveDragFromSideView(cell);
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
                setupHoveringColorChange(cell, Color.LIGHTGREEN);
                receiveDragFromSideView(cell);
                cell.setOnMouseClicked(e -> listeners.forEach(listener -> listener.setOnUpdateStatusEvent(constructStatusView(cell))));
            }
        }
        gameObjectManager.getEntityInstances().clear();
        gameObjectManager.getTileInstances().clear();
    }

    /**
     * This method returns a GridPane listing as entries the GameObjectInstances together with their JavaFx Nodes to be shown in the UpdateStatusEventListener.
     *
     * @return GridPane: The GridPane that contains information about the GameObjectInstances and JavaFx nodes at this cell.
     */
    private GridPane constructStatusView(Region cell) {
        GridPane listView = new GridPane();
        listView.addRow(0, new Label("Node"), new Label("GameObjectInstance"), new Label("Instance ID"));
        cell.getChildrenUnmodifiable().forEach(node -> {
            GameObjectInstance instance = nodeToGameObjectInstanceMap.get(node);
            int id = instance.getInstanceId().getValue();
            listView.addRow(listView.getRowCount(), node, new Text(instance.getClassName().getValue()), new Text(String.valueOf(id)));
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
            GameObjectInstance userObject = nodeToGameObjectInstanceMap.get(targetNode);
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
            editor.editNode(targetNode, userObject);
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
     * @param cell: The input Region where a pair of EventHandlers will be set.
     * @param hoveringColor: The JavaFx Color scheme applied to the hovering.
     */
    private void setupHoveringColorChange(Region cell, Paint hoveringColor) {
        cell.setOnMouseDragEntered(e -> {
            if (e.getGestureSource() instanceof TreeCell) {
                cell.setBackground(new Background(new BackgroundFill(hoveringColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        cell.setOnMouseDragExited(e -> {
            if (e.getGestureSource() instanceof TreeCell) {
                cell.setBackground(Background.EMPTY);
            }
        });
    }

    /**
     * This method sets up a region so that it accepts a MouseDragEvent Released event from the sideview. The Release event will create an instance according to the GameObjectClass from which the drag is initiated.
     *
     * @param cell: A region where the event handler will be set up.
     */
    private void receiveDragFromSideView(Pane cell) {
        cell.setOnMouseDragReleased( e -> {
            if (e.getGestureSource() instanceof TreeCell) {
                TreeItem<String> item = ((TreeCell<String>) e.getGestureSource()).getTreeItem();
                if (!item.isLeaf()) {
                    return;
                }
                GameObjectClass objectClass = gameObjectManager.getGameObjectClass(item.getValue());
                GameObjectType type = objectClass.getType();
                switch (type) {
                    case ENTITY:
                        if (objectClass.getImagePathList().isEmpty()) {
                            Text deploy = new Text(objectClass.getClassName().getValue());
                            deploy.setOnMouseClicked(e1 -> handleDoubleClick(e1, deploy));
                            cell.getChildren().add(deploy);
                            // TODO: get tile id
                            EntityInstance objectInstance = ((EntityClass) objectClass).createInstance(0);
                            nodeToGameObjectInstanceMap.put(deploy, objectInstance);
                        } else {
                            ImageView deploy = new ImageView(new Image(objectClass.getImagePathList().get(0)));
                            deploy.setOnMouseClicked(e1 -> handleDoubleClick(e1, deploy));
                            cell.getChildren().add(deploy);
                            // TODO: get tile id
                            EntityInstance objectInstance = ((EntityClass) objectClass).createInstance(0);
                            nodeToGameObjectInstanceMap.put(deploy, objectInstance);
                        }
                        break;
                    case SOUND:
                        // TODO
                        break;
                    case TILE:
                        break;
                }
            }
        });
    }
}
