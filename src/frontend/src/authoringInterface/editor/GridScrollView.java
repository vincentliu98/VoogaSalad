package authoringInterface.editor;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

/**
 * GridScrollView Class (ScrollPane)
 *      - Representation of the game's grid setting
 *      - It should support Zoom in and zoom out
 *
 * @author Amy Kim
 */
public class GridScrollView implements SubView{
    private ScrollPane gridScrollView;

    public GridScrollView() {
        gridScrollView = new ScrollPane();

        gridScrollView.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        gridScrollView.setHbarPolicy(ScrollBarPolicy.ALWAYS);
    }

    private void handleZoom(){}
    private void dragAndDrop(){}

    @Override
    public Node getView() {
        return gridScrollView;
    }
}
