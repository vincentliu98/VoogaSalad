package authoringInterface.editor.editView;

import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.customEvent.UpdateStatusEventListener;
import authoringInterface.editor.memento.Editor;
import gameObjects.crud.GameObjectsCRUDInterface;
import graphUI.groovy.GroovyPaneFactory;
import graphUI.phase.PhaseChooserPane;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;
import utils.NodeInstanceController;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

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
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
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
        tabPane.getTabs().addAll(mainTab, gridTab, phaseNodeTab);

        mainLabel.setOnMouseReleased(this::detachMain);
        gridLabel.setOnMouseReleased(this::detachGrid);
        phaseLabel.setOnMouseReleased(this::detachPhase);
    }

    private void labelOnTab(Label label, Tab tab) {
        label.getStyleClass().add("tablabel");
        tab.setGraphic(label);
    }

    private Stage splitTab(Tab tab) {
            tabPane.getTabs().remove(tab);
            Stage newStage = new Stage();
            TabPane newTabPane = new TabPane();
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

    private void detachMain(MouseEvent e) {
        Point2D mouseLoc = new Point2D(e.getScreenX(), e.getScreenY());
        Window window = tabPane.getScene().getWindow();
        Rectangle2D windowBounds
                = new Rectangle2D(window.getX(), window.getY(),
                window.getWidth(), window.getHeight());
        if (!windowBounds.contains(mouseLoc)) {
            splitTab(mainTab).show();
        }
    }

    private void detachGrid(MouseEvent e) {
        Point2D mouseLoc = new Point2D(e.getScreenX(), e.getScreenY());
        Window window = tabPane.getScene().getWindow();
        Rectangle2D windowBounds
                = new Rectangle2D(window.getX(), window.getY(),
                window.getWidth(), window.getHeight());
        if (!windowBounds.contains(mouseLoc)) {
            splitTab(gridTab).show();
        }
    }

    private void detachPhase(MouseEvent e) {
        Point2D mouseLoc = new Point2D(e.getScreenX(), e.getScreenY());
        Window window = tabPane.getScene().getWindow();
        Rectangle2D windowBounds
                = new Rectangle2D(window.getX(), window.getY(),
                window.getWidth(), window.getHeight());
        if (!windowBounds.contains(mouseLoc)) {
            splitTab(phaseNodeTab).show();
        }
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
