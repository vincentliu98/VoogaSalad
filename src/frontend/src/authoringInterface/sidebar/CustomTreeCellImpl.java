package authoringInterface.sidebar;

import authoringInterface.sidebar.subEditors.EntityEditor;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Map;

/**
 * This class organizes the cell factory call back methods into a nicer format.
 *
 * @author Haotian Wang
 */
public class CustomTreeCellImpl extends TreeCell<String> {
    private Map<String, EditTreeItem> objectMap;
    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();

    public CustomTreeCellImpl(Map<String, EditTreeItem> map) {
        objectMap = map;
        MenuItem addMenuItem = new MenuItem("Add an entry");
        addMenuItem.setOnAction(e -> {
            int id = getTreeItem().getChildren().size();
            switch (getItem()) {
                case "ENTITY":
                    Stage dialogStage = new Stage();
                    EntityEditor editor = new EntityEditor();
                    dialogStage.setScene(new Scene(editor.getView(), 500, 500));
                    dialogStage.show();
                    editor.addTreeItem(getTreeItem(), objectMap);
                    break;
                case "SOUND":
                    break;
                case "TILE":
                    break;
                case "User Settings":
                    break;
            }
        });
        addMenu.getItems().add(addMenuItem);
        setOnDragDetected(e -> startFullDrag());
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
                }
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                objectMap.put(textField.getText(), objectMap.get(getItem()));
                objectMap.remove(getItem());
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

