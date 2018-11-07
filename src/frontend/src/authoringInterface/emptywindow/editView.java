package authoringInterface.emptywindow;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.TabPane;


/**
 * editView Class (TabPane > ScrollPane)
 *      - Representation of the game setting (e.g. entities, grids)
 *      - It should support Zoom in and zoom out
 *
 * @author Amy Kim
 */
public class editView implements SubView {
    private TabPane tabPane;


    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the empty window.
     */
    public editView(){

    }


    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public Node getView() {
        return tabPane;
    }
}
