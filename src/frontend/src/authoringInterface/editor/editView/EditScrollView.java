package authoringInterface.editor.editView;

import api.DraggingCanvas;
import api.SubView;
import authoringInterface.sidebar.SideView;
import authoringInterface.sidebar.SideViewInterface;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
    private Map<Node, TreeItem<String>> nodeToTreeItemMap;

    public EditScrollView(SideViewInterface sideView) {
        this.sideView = sideView;
        nodeToTreeItemMap = new HashMap<>();
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
                    nodeToTreeItemMap.put(copy, item);
                } else {
                    Text target = new Text(item.getValue());
                    target.setX(e.getX());
                    target.setY(e.getY());
                    gridScrollView.getChildren().add(target);
                    nodeToTreeItemMap.put(target, item);
                }
            }
        });
    }

    public void handleDoubleClick(ActionEvent event) {

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
