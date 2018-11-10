package authoringInterface;

import api.SubView;
import authoringInterface.editor.EmptySkeleton;
import javafx.scene.Parent;

/**
 * This class creates the default window for the authoring engine, built upon the EmptySkeleton.
 *
 * @author Haotian Wang
 */
public class DefaultWindow implements SubView<Parent> {
    private EmptySkeleton emptySkeleton;

    public DefaultWindow() {
        emptySkeleton = new EmptySkeleton();
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public Parent getView() {
        return (Parent) emptySkeleton.getView();
    }
}
