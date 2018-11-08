package authoringInterface.emptywindow;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;


/**
 * EditView Class (TabPane > ScrollPane)
 *      - holding scroll views
 *
 * @author Amy Kim
 */
public class EditView implements SubView {
    private TabPane tabPane;
    //TODO: ScrollViews can be more; need to decide
    private EntityScrollView entityScrollView;
    private GridScrollView gridScrollView;

    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the empty window.
     */
    public EditView(){
        tabPane = new TabPane();
        entityScrollView = new EntityScrollView();
        gridScrollView = new GridScrollView();

        constructTab();
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }


    private void constructTab(){
        var entityTab = new Tab("Entity");
        var gridTab = new Tab("Grid");

        entityTab.setContent(entityScrollView.getView());
        gridTab.setContent(gridScrollView.getView());

        tabPane.getTabs().add(entityTab);
        tabPane.getTabs().add(gridTab);

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
