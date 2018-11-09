package authoringInterface.editor;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;

/**
 * GridScrollView Class (ScrollPane)
 *      - Representation of the game's grid setting
 *      - It should support Zoom in and zoom out
 *
 * @author Amy Kim
 */
public class GridScrollView implements SubView{
    private ScrollPane gridScrollView;
    private HBox contentBox;

    public GridScrollView() {
        gridScrollView = new ScrollPane();
        contentBox = new HBox();

        gridScrollView.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        gridScrollView.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

        gridScrollView.setContent(contentBox);
    }

    private void handleZoom(){}
    private void dragAndDrop(){}

    @Override
    public Node getView() {
        return gridScrollView;
    }
}
