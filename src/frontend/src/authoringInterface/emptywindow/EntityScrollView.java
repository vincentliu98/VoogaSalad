package authoringInterface.emptywindow;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * EntityScrollView Class (ScrollPane)
 *      - Representation of the game's entity setting
 *      - It should support Zoom in and zoom out
 *
 * @author Amy Kim
 */
public class EntityScrollView implements SubView {
    private ScrollPane entityScrollView;

    private ScrollPane constructScrollPane() {
        var entityScrollView = new ScrollPane();
        final Rectangle rect = new Rectangle(200, 200, 800, 600);
        rect.setFill(Color.RED);
        entityScrollView.setContent(rect);
        return entityScrollView;
    }

    @Override
    public Node getView() {
        return entityScrollView;
    }
}
