package authoringInterface.sidebar.subEditors;

import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import javafx.scene.layout.AnchorPane;

public class TileEditor implements ObjectEditor<AnchorPane> {
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
        return null;
    }
}
