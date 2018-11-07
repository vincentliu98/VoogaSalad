package authoringInterface.emptywindow;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * This class provides an empty skeleton window with the basic menu items.
 *
 * @author  Haotian Wang
 */
public class EmptySkeleton implements SubView {
    private AnchorPane rootPane;


    @Override
    public Node getView() {
        return rootPane;
    }
}
