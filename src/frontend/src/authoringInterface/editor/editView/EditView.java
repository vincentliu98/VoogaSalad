package authoringInterface.editor.editView;

import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.customEvent.UpdateStatusEventListener;
import authoringInterface.editor.memento.Editor;
import gameObjects.crud.GameObjectsCRUDInterface;
import graphUI.groovy.GroovyPaneFactory;
import graphUI.phase.PhaseChooserPane;
import javafx.scene.Node;
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
 * @author Haotian Wang
 */

public class EditView implements SubView<TabPane> {
    private final TabPane tabPane = new TabPane();
    private AuthoringTools authTools;
    private final Editor editor = new Editor();
    private GroovyPaneFactory groovyPaneFactory;
    private GameObjectsCRUDInterface objectManager;
    private int rowNumber;
    private int colNumber;
    private EditGridView gridView;

    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the createGraph window.
     */
    public EditView(AuthoringTools authTools, GroovyPaneFactory groovyPaneFactory, int row, int col, GameObjectsCRUDInterface manager){
        this.authTools = authTools;
        this.groovyPaneFactory = groovyPaneFactory;
        objectManager = manager;
        rowNumber = row;
        colNumber = col;
        initializeTab();
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.setOnDragDetected(e -> System.out.println("draggedOver"));
    }

    private void initializeTab(){
        Tab mainTab = new Tab("Main");
        mainTab.setContent(new MainTabView().getView());

        Tab GridTab = new Tab("Grid");
        gridView = new EditGridView(rowNumber, colNumber, objectManager);
        GridTab.setContent(gridView.getView());

        Tab PhaseTab = new Tab("Phase");
        PhaseTab.setContent(
               new PhaseChooserPane(
                       authTools.phaseDB(),
                       groovyPaneFactory::gen
               ).getView()
        );

        tabPane.getTabs().addAll(mainTab, GridTab, PhaseTab);
    }

    public void updateDimension(int width, int height) {
        gridView.updateDimension(width, height);
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

    /**
     * This method uses the proxy pattern to pass the registration of listener to the child GridPane.
     *
     * @param listener: The listener to be registered by the child GridPane.
     */
    public void addUpdateStatusEventListener(UpdateStatusEventListener<Node> listener) {
        gridView.addUpdateStatusEventListener(listener);
    }
}
