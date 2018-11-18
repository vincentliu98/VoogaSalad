package authoringInterface.editor.editView;

import api.DraggingCanvas;
import api.SubView;
import authoringInterface.sidebar.SideView;
import authoringInterface.sidebar.SideViewInterface;
import authoringInterface.sidebar.subEditors.AbstractObjectEditor;
import authoringInterface.sidebar.subEditors.EntityEditor;
import authoringInterface.sidebar.subEditors.ObjectEditor;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import authoringInterface.sidebar.treeItemEntries.Entity;
import authoringInterface.sidebar.treeItemEntries.TreeItemType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * EditScrollView Class (ScrollPane)
 *      - Representation of the game's grid setting
 *      - It should support Zoom in and zoom out
 *
 * @author Amy Kim, Haotian Wang
 */
public class EditScrollView implements SubView<Pane>, DraggingCanvas {
    private Pane gridScrollView;
    private HBox contentBox;
    private SideViewInterface sideView;
    private Map<Node, EditTreeItem> nodeToObjectMap;

    public EditScrollView(SideViewInterface sideView) {
        this.sideView = sideView;
        nodeToObjectMap = new HashMap<>();
        gridScrollView = new Pane();
        contentBox = new HBox();
        gridScrollView.addEventFilter(MouseDragEvent.MOUSE_DRAG_RELEASED, e -> {
            if (e.getGestureSource() instanceof TreeCell) {
                TreeItem<String> item = ((TreeCell<String>) e.getGestureSource()).getTreeItem();
                if (!item.isLeaf()) {
                    return;
                }
                if (item.getGraphic() != null) {
                    ImageView copy = new ImageView(((ImageView) item.getGraphic()).getImage());
                    copy.setX(e.getX());
                    copy.setY(e.getY());
                    gridScrollView.getChildren().add(copy);
                    nodeToObjectMap.put(copy, sideView.getObject(item.getValue()));
                    copy.setOnMouseClicked(event -> handleDoubleClick(event, copy));
                } else {
                    Text target = new Text(item.getValue());
                    target.setX(e.getX());
                    target.setY(e.getY());
                    gridScrollView.getChildren().add(target);
                    nodeToObjectMap.put(target, sideView.getObject(item.getValue()));
                    target.setOnMouseClicked(ee -> handleDoubleClick(ee, target));
                }
            }
        });
    }

    /**
     * This method opens an editor window if the user wishes to double click on an object already dropped on the grid.
     *
     * @param event: A MouseEvent event, which is a double click.
     * @param targetNode: The JavaFx Node where this double click occurs.
     */
    private void handleDoubleClick(MouseEvent event, Node targetNode) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            EditTreeItem userObject = nodeToObjectMap.get(targetNode);
            TreeItemType type = userObject.getType();
            Stage dialogStage = new Stage();
            AbstractObjectEditor editor = null;
            switch (type) {
                case ENTITY:
                    editor = new EntityEditor();
                    editor.readObject(userObject);
                case SOUND:
                    break;
                case TILE:
                    break;
                case CATEGORY:
                    break;
            }
            editor.editNode(targetNode, nodeToObjectMap);
            dialogStage.setScene(new Scene(editor.getView(), 500, 500));
            dialogStage.show();
        }
    }

    @Override
    public Pane getView() {
        return gridScrollView;
    }

    /**
     * Setup the dragging canvas event filters.
     */
    @Override
    public void setupDraggingCanvas() {

    }
}
