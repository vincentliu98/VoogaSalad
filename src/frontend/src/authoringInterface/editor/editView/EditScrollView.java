package authoringInterface.editor.editView;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;

/**
 * EditScrollView Class (ScrollPane)
 *      - Representation of the game's grid setting
 *      - It should support Zoom in and zoom out
 *
 * @author Amy Kim
 */
public class EditScrollView implements SubView<ScrollPane> {
    private ScrollPane gridScrollView;
    private HBox contentBox;

    public EditScrollView() {
        gridScrollView = new ScrollPane();
        contentBox = new HBox();

        gridScrollView.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        gridScrollView.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

        gridScrollView.setContent(contentBox);
    }

    private void handleZoom(){}
    private void dragAndDrop(){}

    @Override
    public ScrollPane getView() {
        return gridScrollView;
    }
}
