package authoringInterface.sidebar.subEditors;

import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import javafx.scene.layout.AnchorPane;

/**
 * This is the editor for an "Entity" object that is opened when the user clicks on an existing entity or tries to add an entity to the game authoring library.
 *
 * @author Haotian Wang
 */
public class EntityEditor implements ObjectEditor<AnchorPane> {
    private AnchorPane rootPane;

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    @Override
    public void readObject(EditTreeItem userObject) {

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
}
