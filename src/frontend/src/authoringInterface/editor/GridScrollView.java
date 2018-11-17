package authoringInterface.editor;

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
 * GridScrollView Class (ScrollPane)
 *      - Representation of the game's grid setting
 *      - It should support Zoom in and zoom out
 *
 * @author Amy Kim, Haotian Wang
 */
public class GridScrollView implements SubView<Pane> {
    private Pane gridScrollView;
    private HBox contentBox;

    public GridScrollView() {
        gridScrollView = new Pane();
        contentBox = new HBox();
        gridScrollView.addEventFilter(MouseDragEvent.MOUSE_DRAG_RELEASED, e -> {
            if (e.getGestureSource() instanceof TreeCell) {
                TreeItem<String> item = ((TreeCell) e.getGestureSource()).getTreeItem();
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
//        gridScrollView.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
//        gridScrollView.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
//
//        gridScrollView.setContent(contentBox);
    }

    private void handleZoom(){}
    private void dragAndDrop(){}

    @Override
    public Pane getView() {
        return gridScrollView;
    }
}
