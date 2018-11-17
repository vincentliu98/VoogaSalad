package authoringInterface.sidebar.subEditors;

import api.SubView;
import authoringInterface.sidebar.treeItemEntries.Category;
import javafx.scene.layout.AnchorPane;

/**
 * This class handles the addition and editing of category entries.
 *
 * @author Haotian Wang
 */
public class CategoryEditor extends AbstractObjectEditor implements ObjectEditor<Category>, SubView<AnchorPane> {
    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return null;
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    @Override
    public void readObject(Category userObject) {

    }

    /**
     * Return the object after edits in this ObjectEditor.
     *
     * @return A specific user object.
     */
    @Override
    public Category getObject() {
        return null;
    }

    /**
     * Return a boolean indicating whether the changes are successfully applied.
     *
     * @return
     */
    @Override
    public boolean applied() {
        return isApplied;
    }
}
