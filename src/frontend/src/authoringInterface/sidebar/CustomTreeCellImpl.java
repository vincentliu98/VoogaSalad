package authoringInterface.sidebar;

import authoringInterface.subEditors.*;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.gameObject.GameObjectClass;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import utils.NodeInstanceController;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

/**
 * This class organizes the cell factory call back methods into a nicer format.
 *
 * @author Haotian Wang
 */
public class CustomTreeCellImpl extends TreeCell<String> {
    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();
    private ContextMenu editMenu = new ContextMenu();
    private GameObjectsCRUDInterface objectManager;

    public CustomTreeCellImpl(GameObjectsCRUDInterface manager) {
        objectManager = manager;
        MenuItem addMenuItem = new MenuItem("Add an entry");
        addMenuItem.setOnAction(e -> {
            switch (getItem()) {
                case "ENTITY":
                    Stage dialogStage = new Stage();
                    EntityEditor editor = new EntityEditor(manager);
                    dialogStage.setScene(new Scene(editor.getView(), 500, 500));
                    dialogStage.show();
                    editor.addTreeItem(getTreeItem());
                    break;
                case "TILE":
                    Stage dialogTileStage = new Stage();
                    TileEditor tileEditor = new TileEditor(manager);
                    dialogTileStage.setScene(new Scene(tileEditor.getView(), 500, 500));
                    dialogTileStage.show();
                    tileEditor.addTreeItem(getTreeItem());
                case "User Settings":
                    break;
            }
        });
        addMenu.getItems().add(addMenuItem);
        setOnDragDetected(e -> startFullDrag());
        MenuItem editMenuItem = new MenuItem("Edit this GameObject");
        MenuItem deleteMenuItem = new MenuItem("Delete this GameObject");
        editMenuItem.setOnAction(e -> {
            GameObjectClass objectClass = objectManager.getGameObjectClass(getItem());
            Stage dialogStage = new Stage();
            AbstractGameObjectEditor editor = null;
            switch (objectClass.getType()) {
                case ENTITY:
                    editor = new EntityEditor(objectManager);
                    break;
                case CATEGORY:
                    // TODO
                    break;
                case TILE:
                    // TODO: 11/30/18 Finish TileEditor
                    editor = new TileEditor(objectManager);
                    break;
            }
            dialogStage.setScene(new Scene(editor.getView(), 500, 500));
            dialogStage.show();
            editor.editTreeItem(getTreeItem(), objectClass);
        });
        deleteMenuItem.setOnAction(e -> {
            objectManager.deleteGameObjectClass(getItem());
            getTreeItem().getParent().getChildren().remove(getTreeItem());
        });
        editMenu.getItems().addAll(editMenuItem, deleteMenuItem);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
                if (!getTreeItem().isLeaf()) {
                    setContextMenu(addMenu);
                } else {
                    setContextMenu(editMenu);
                }
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                objectManager.changeGameObjectClassName(getItem(), textField.getText());
                commitEdit(textField.getText());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });

    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}

