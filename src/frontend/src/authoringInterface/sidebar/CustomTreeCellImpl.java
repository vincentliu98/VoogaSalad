package authoringInterface.sidebar;

import authoringInterface.subEditors.AbstractGameObjectEditor;
import authoringInterface.subEditors.EditorFactory;
import authoringInterface.subEditors.exception.MissingEditorForTypeException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectInstanceNotFoundException;
import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;
import gameplay.GameObject;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import utils.exception.PreviewUnavailableException;
import utils.exception.UnremovableNodeException;
import utils.imageManipulation.ImageManager;
import utils.imageManipulation.JavaFxOperation;
import utils.nodeInstance.NodeInstanceController;


/**
 * This class organizes the cell factory call back methods into a nicer format.
 *
 * @author Haotian Wang
 */
public class CustomTreeCellImpl extends TreeCell<String> {
    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();
    private ContextMenu editMenu = new ContextMenu();
    private static final double ICON_HEIGHT = 50;
    private static final double ICON_WIDTH = 50;
    private GameObjectsCRUDInterface objectManager;
    private NodeInstanceController nodeInstanceController;

    public CustomTreeCellImpl(GameObjectsCRUDInterface manager, NodeInstanceController controller) {
        objectManager = manager;
        nodeInstanceController = controller;
        MenuItem addMenuItem = new MenuItem("Add an entry");
        addMenuItem.setOnAction(e -> {
            try {
                Stage dialogStage = new Stage();
                AbstractGameObjectEditor editor = EditorFactory.makeEditor(getItem(), manager);
                dialogStage.setScene(new Scene(editor.getView(), 600, 620));
                dialogStage.show();
                editor.addTreeItem(getTreeItem());

            } catch (GameObjectTypeException e1) {
                // TODO
            } catch (MissingEditorForTypeException e1) {
                // TODO
                e1.printStackTrace();
            }
        });
        addMenu.getItems().add(addMenuItem);
        setOnDragDetected(e -> {
            GameObjectClass draggedClass = null;
            try {
                draggedClass = objectManager.getGameObjectClass(getString());
            } catch (GameObjectClassNotFoundException e1) {
                // TODO
                e1.printStackTrace();
            }
            assert draggedClass != null;
            if (draggedClass.getType() == GameObjectType.PLAYER || draggedClass.getType() == GameObjectType.CATEGORY) {
                e.consume();
                return;
            }
            Dragboard db = startDragAndDrop(TransferMode.ANY);
            ClipboardContent cc = new ClipboardContent();
            cc.putString(getString());
            db.setContent(cc);
            try {
                db.setDragView(ImageManager.getPreview(draggedClass));
            } catch (PreviewUnavailableException e1) {
                // TODO: proper error handling.
                e1.printStackTrace();
            }
            e.consume();
        });
        MenuItem editMenuItem = new MenuItem("Edit this GameObject");
        MenuItem deleteMenuItem = new MenuItem("Delete this GameObject");
        editMenuItem.setOnAction(e -> {
            GameObjectClass objectClass = null;
            try {
                objectClass = objectManager.getGameObjectClass(getItem());
            } catch (GameObjectClassNotFoundException e1) {
                // TODO
                e1.printStackTrace();
            }
            Stage dialogStage = new Stage();
            AbstractGameObjectEditor editor = null;
            try {
                editor = EditorFactory.makeEditor(objectClass.getType(), manager);
            } catch (MissingEditorForTypeException e1) {
                // TODO
                e1.printStackTrace();
            }
            dialogStage.setScene(new Scene(editor.getView(), 600, 620));
            dialogStage.show();
            editor.editTreeItem(getTreeItem(), objectClass);
        });
        deleteMenuItem.setOnAction(e -> {
            try {
                objectManager.getGameObjectClass(getItem()).getAllInstances().forEach(gameObjectInstance -> {
                    Node node = null;
                    try {
                        node = nodeInstanceController.getNode(gameObjectInstance);
                    } catch (GameObjectInstanceNotFoundException e1) {
                        // TODO: proper error handling
                        e1.printStackTrace();
                    }
                    try {
                        assert node != null;
                        JavaFxOperation.removeFromParent(node);
                    } catch (UnremovableNodeException e1) {
                        // TODO: proper error handling
                        e1.printStackTrace();
                    }
                    try {
                        nodeInstanceController.removeGameObjectInstance(gameObjectInstance);
                    } catch (GameObjectInstanceNotFoundException e1) {
                        // TODO: proper error handling
                        e1.printStackTrace();
                    }
                });
            } catch (GameObjectClassNotFoundException e1) {
                // TODO: proper error handling
                e1.printStackTrace();
            }
            objectManager.deleteGameObjectClass(getItem());
            getTreeItem().getParent().getChildren().remove(getTreeItem());
        });
        editMenu.getItems().addAll(editMenuItem, deleteMenuItem);
    }

    @Override
    public void startEdit() {
        try {
            if (objectManager.getGameObjectClass(getString()).getType() == GameObjectType.CATEGORY) {
                return;
            }
        } catch (GameObjectClassNotFoundException ignored) {
        }
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
                try {
                    if (objectManager.getGameObjectClass(getString()).getType() == GameObjectType.CATEGORY) {
                        setContextMenu(addMenu);
                    } else {
                        setContextMenu(editMenu);
                    }
                } catch (GameObjectClassNotFoundException ignored) {}
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                try {
                    objectManager.changeGameObjectClassName(getItem(), textField.getText());

                } catch (InvalidOperationException e) {
                    // TODO
                    e.printStackTrace();
                }
                GameObjectClass gameObjectClass = null;
                try {
                    gameObjectClass = objectManager.getGameObjectClass(textField.getText());
                } catch (GameObjectClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    ImageManager.removeClassImage(gameObjectClass);
                } catch (GameObjectClassNotFoundException ignored) {}
                ImageView icon = null;
                try {
                    icon = new ImageView(ImageManager.getPreview(gameObjectClass));
                } catch (PreviewUnavailableException e) {
                    e.printStackTrace();
                }
                assert icon != null;
                JavaFxOperation.setWidthAndHeight(icon, ICON_WIDTH, ICON_HEIGHT);
                getTreeItem().setGraphic(icon);
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

