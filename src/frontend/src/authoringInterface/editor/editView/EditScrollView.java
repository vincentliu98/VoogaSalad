package authoringInterface.editor.editView;

import api.DraggingCanvas;
import api.SubView;
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

    public EditScrollView() {
        gridScrollView = new Pane();
        contentBox = new HBox();
        gridScrollView.addEventFilter(MouseDragEvent.MOUSE_DRAG_RELEASED, e -> {
            if (e.getGestureSource() instanceof TreeCell) {
                TreeItem<String> item = ((TreeCell) e.getGestureSource()).getTreeItem();
                if (item.getChildren().size() != 0) {
                    return;
                }
                if (item.getGraphic() != null) {
                    ImageView copy = new ImageView(((ImageView) item.getGraphic()).getImage());
                    copy.setX(e.getX());
                    copy.setY(e.getY());
                    gridScrollView.getChildren().add(copy);
                } else {
                    Text target = new Text(item.getValue());
                    target.setX(e.getX());
                    target.setY(e.getY());
                    gridScrollView.getChildren().add(target);
                }
            }
        });
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
