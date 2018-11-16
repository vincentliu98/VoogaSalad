package authoringInterface.sidebar.newSideView;

import api.SubView;
import javafx.scene.layout.StackPane;

/**
 * This class represents a new SideView implementation that has a JavaFx TreeView object inside, but with cleaner implementation.
 *
 * @author Haotian Wang
 */
public class NewSideView implements SubView<StackPane> {
    private StackPane sidePane;

    public NewSideView() {
        sidePane = new StackPane();
    }
    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public StackPane getView() {
        return sidePane;
    }
}
