package authoringInterface.editor.editView;

import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.customEvent.UpdateStatusEventListener;
import authoringInterface.editor.memento.Editor;
import gameObjects.crud.GameObjectsCRUDInterface;
import graphUI.groovy.GroovyPaneFactory;
import graphUI.phase.PhaseChooserPane;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.StackPane;
import utils.nodeInstance.NodeInstanceController;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * EditView Class (TabPane > Pane)
 *      - holding scroll views
 *
 * @author Amy Kim
 * @author jl729
 * @author Haotian Wang
 */

public class EditView implements SubView<TabPane> {
    public static final String STYLESHEET = "style.css";
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 700;

    private final TabPane tabPane = new TabPane();
    private AuthoringTools authTools;
    private final Editor editor = new Editor();
    private GroovyPaneFactory groovyPaneFactory;
    private GameObjectsCRUDInterface objectManager;
    private int rowNumber;
    private int colNumber;
    private EditGridView gridView;
    private NodeInstanceController nodeInstanceController;
    private PhaseChooserPane phaseView;
    private Tab mainTab;
    private Tab phaseNodeTab;
    private Tab gridTab;
    private Tab endHandlerTab;
    private Integer index;
    private Stage newStage;
    private TabPane newTabPane;

    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the createGraph window.
     */
    public EditView(AuthoringTools authTools, GroovyPaneFactory groovyPaneFactory, int row, int col, GameObjectsCRUDInterface manager, NodeInstanceController controller) {
        this.authTools = authTools;
        this.groovyPaneFactory = groovyPaneFactory;
        nodeInstanceController = controller;
        objectManager = manager;
        rowNumber = row;
        colNumber = col;
        initializeTab();
        tabPane.setTabDragPolicy(TabDragPolicy.FIXED);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    private void initializeTab(){
        mainTab = new Tab();
        Label mainLabel = new Label("Main");
        labelOnTab(mainLabel, mainTab);
        mainTab.setContent(new MainTabView().getView());

        gridTab = new Tab();
        Label gridLabel = new Label("Grid");
        labelOnTab(gridLabel, gridTab);
        gridView = new EditGridView(rowNumber, colNumber, objectManager, nodeInstanceController);
        gridTab.setContent(gridView.getView());

        phaseNodeTab = new Tab();
        Label phaseLabel = new Label("Phase");
        labelOnTab(phaseLabel, phaseNodeTab);
        phaseView = new PhaseChooserPane(
                authTools.phaseDB(),
                groovyPaneFactory::gen
        );
        phaseNodeTab.setContent(phaseView.getView());

        endHandlerTab = new Tab();
        Label endHandlerLabel = new Label("Ending");
        labelOnTab(endHandlerLabel, endHandlerTab);
        var dialog = groovyPaneFactory.endHandler();
        dialog.closeWindow(); // we're only gonna use its view
        endHandlerTab.setContent(dialog.getView());

        tabPane.getTabs().addAll(mainTab, gridTab, phaseNodeTab, endHandlerTab);

//        mainLabel.setOnMouseDragged(e -> {
//            index = 0;
//            Point2D mouseLoc = new Point2D(e.getScreenX(), e.getScreenY());
//            splitTab(mouseLoc, mainTab, index);
//        });
        gridLabel.setOnMouseDragged(e -> {
            index = 1;
            Point2D mouseLoc = new Point2D(e.getScreenX(), e.getScreenY());
            splitTab(mouseLoc, gridTab, index);
        });
        phaseLabel.setOnMouseDragged(e -> {
            index = 2;
            Point2D mouseLoc = new Point2D(e.getScreenX(), e.getScreenY());
            splitTab(mouseLoc, phaseNodeTab, index);
        });
    }
    
    private void labelOnTab(Label label, Tab tab) {
        label.getStyleClass().add("tablabel");
        tab.setGraphic(label);
    }

    private void splitTab(Point2D mouseLoc, Tab tab, int index) {
        StackPane header = (StackPane) tabPane.lookup(".tab-header-area");
        if(!header.getBoundsInLocal().contains(mouseLoc)){
            openModal(tab).show();
        }
    }


    private Stage openModal(Tab tab) {
            tabPane.getTabs().remove(tab);
            newTabPane = new TabPane();
            newStage = new Stage();
            newTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
            newTabPane.getTabs().add(tab);
            Scene scene = new Scene(new BorderPane(newTabPane), SCREEN_WIDTH, SCREEN_HEIGHT);
            scene.getStylesheets().add(STYLESHEET);
            newStage.setScene(scene);
            newStage.setOnCloseRequest(e -> detachOnTab(tab));
            return newStage;
    }

    private void detachOnTab(Tab tab) {
        tabPane.getTabs().add(tab);
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
