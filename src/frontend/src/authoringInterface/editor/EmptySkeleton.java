package authoringInterface.editor;

import api.ParentView;
import api.SubView;
import javafx.scene.layout.AnchorPane;


/**
 * This class provides an empty skeleton window with the basic menu items, and basic editing interfaces.
 *
 * @author  Haotian Wang
 */
public class EmptySkeleton implements SubView<AnchorPane>, ParentView<SubView> {
    private AnchorPane rootPane;

    public EmptySkeleton() {
        rootPane = new AnchorPane();
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return rootPane;
    }

    /**
     * Add the JavaFx Node representation of a subView into the parent View in a hierarchical manner.
     *
     * @param view: A SubView object.
     */
    @Override
    public void addChild(SubView view) {
        rootPane.getChildren().add(view.getView());
    }
}
