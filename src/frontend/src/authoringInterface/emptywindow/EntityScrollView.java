package authoringInterface.emptywindow;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

/**
 * EntityScrollView Class (ScrollPane)
 *      - Representation of the game's entity setting
 *      - It should support Zoom in and zoom out
 *
 * @author Amy Kim
 */
public class EntityScrollView implements SubView {
    private ScrollPane entityScrollView;

    public EntityScrollView() {
        entityScrollView = new ScrollPane();

        entityScrollView.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        entityScrollView.setHbarPolicy(ScrollBarPolicy.ALWAYS);
    }

    private void handleZoom(){}
    private void dragAndDrop(){}

    @Override
    public Node getView() {
        return entityScrollView;
    }
}
