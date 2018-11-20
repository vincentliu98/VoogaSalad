package authoringInterface.editor.editView;

import api.SubView;
import authoringInterface.sidebar.SideViewInterface;
import graphUI.phase.PhasePane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;


/**
 * EditView Class (TabPane > Pane)
 *      - holding scroll views
 *
 * @author Amy Kim
 * @author jl729
 */

public class EditView implements SubView<TabPane> {
    private final TabPane tabPane = new TabPane();
    private SideViewInterface sideView;

    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the createGraph window.
     * @param sideView
     */
    public EditView(SideViewInterface sideView){
        this.sideView = sideView;
        initializeTab();
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    private void initializeTab(){
        Tab GridTab = new Tab("Grid");
        GridTab.setContent(new EditGridView(sideView).getView());

        Tab EntityTab = new Tab("Entity");
        EntityTab.setContent(new EditEntityView(sideView).getView());

        Tab PhaseTab = new Tab("Phase");

        Tab LevelTab = new Tab("Level");

        tabPane.getTabs().addAll(GridTab,EntityTab,PhaseTab, LevelTab);
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public TabPane getView() {
        return tabPane;
    }
}
