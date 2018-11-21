package authoringInterface.editor.editView;

import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.editor.memento.Editor;
import authoringInterface.sidebar.SideViewInterface;
import graphUI.groovy.GroovyPaneFactory;
import graphUI.phase.PhasePane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;


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
    private AuthoringTools authTools;
    private final Editor editor = new Editor();
    private GroovyPaneFactory groovyPaneFactory;

    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the createGraph window.
     * @param sideView
     */
    public EditView(SideViewInterface sideView, AuthoringTools authTools,  GroovyPaneFactory groovyPaneFactory){
        this.sideView = sideView;
        this.authTools = authTools;
        this.groovyPaneFactory = groovyPaneFactory;
        editor.setState(authTools.globalData());
        initializeTab();
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    private void initializeTab(){
        Tab mainTab = new Tab("Main");
        mainTab.setContent(new MainTabView().getView());

        Tab GridTab = new Tab("Grid");
        GridTab.setContent(new EditGridView(sideView).getView());

        Tab EntityTab = new Tab("Entity");
        EntityTab.setContent(new EditEntityView(sideView).getView());

        Tab PhaseTab = new Tab("Phase");
        PhaseTab.setContent(
                new PhasePane(
                        authTools.phaseDB(),
                        groovyPaneFactory::gen)
                        .getView());
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
