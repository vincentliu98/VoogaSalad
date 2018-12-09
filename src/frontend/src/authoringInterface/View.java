package authoringInterface;

import api.ParentView;
import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.editor.editView.EditView;
import authoringInterface.editor.menuBarView.EditorMenuBarView;
import authoringInterface.sidebar.SideView;
import authoringInterface.sidebar.StatusView;
import com.thoughtworks.xstream.XStream;
import gameObjects.crud.GameObjectsCRUDInterface;
import graphUI.groovy.GroovyPaneFactory;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.nodeInstance.CrappyNodeInstanceController;
import utils.nodeInstance.NodeInstanceController;
import utils.serializerTest.SerializerTestCRUD;

import java.io.*;

/**
 * This class provides an createGraph skeleton window with the basic menu items, and basic editing interfaces.
 *
 * @author Haotian Wang
 * @author jl729
 * @author Amy
 */
public class View implements ParentView<SubView> {
    private AnchorPane rootPane;
    private EditorMenuBarView menuBar;
    private SideView sideView;
    private GroovyPaneFactory groovyPaneFactory;
    private EditView editView;
    private Stage primaryStage;
    private AuthoringTools tools;
    private Node preview;
    private StatusView statusView;
    private GridPane sidebar;
    private GridPane mainView;
    private NodeInstanceController nodeInstanceController;
    private GameObjectsCRUDInterface gameObjectManager;
    public static final double MENU_BAR_HEIGHT = 30;
    public static final double GAME_WIDTH = 700;
    public static final double GAME_HEIGHT = 500;
    private static final int ROW_NUMBER = 10;
    private static final int COL_NUMBER = 7;
    private static final double SIDEBAR_WIDTH = 250;

    /**
     * Constructor for an createGraph window, with an AnchorPane as the root Node, and the AnchorPane constraints on top, left and right are 0.
     */
    public View(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainView = new GridPane();
        rootPane = new AnchorPane();
        rootPane.getStyleClass().add("mainPane");
        tools = new AuthoringTools(COL_NUMBER, ROW_NUMBER);
        gameObjectManager = tools.entityDB();
        groovyPaneFactory = new GroovyPaneFactory(primaryStage, tools.factory(), tools.phaseDB().winCondition());
        nodeInstanceController = new CrappyNodeInstanceController();
        initializeElements();
        setElements();
        addElements();
    }

    private void initializeElements() {
        sidebar = new GridPane();
        sideView = new SideView(gameObjectManager, nodeInstanceController);
        editView = new EditView(tools, groovyPaneFactory, ROW_NUMBER, COL_NUMBER, gameObjectManager, nodeInstanceController);
        statusView = new StatusView(gameObjectManager);
        editView.addUpdateStatusEventListener(statusView);
        menuBar = new EditorMenuBarView(tools, primaryStage::close, this::updateGridDimension, editView);
        sidebar.addColumn(0, sideView.getView(), statusView.getView());
        mainView.getColumnConstraints().addAll(new ColumnConstraints(MainAuthoringProgram.SCREEN_WIDTH - SIDEBAR_WIDTH));
        mainView.addColumn(0, editView.getView());
        mainView.addColumn(1, sidebar);
        rootPane.addEventFilter(KeyEvent.KEY_PRESSED, e-> {
            if (e.getCode() == KeyCode.K) {
                XStream crudXstream = new SerializerTestCRUD().getSerializer();
                System.out.println(crudXstream.toXML(gameObjectManager));
            }
        });
    }

    private void updateGridDimension(Integer width, Integer height) {
        tools.setGridDimension(width, height);
        editView.updateDimension(width, height);
    }

    /**
     * Set the positions of the components in an AnchorPane.
     */
    private void setElements() {
        AnchorPane.setLeftAnchor(menuBar.getView(), 0.0);
        AnchorPane.setRightAnchor(menuBar.getView(), 0.0);
        AnchorPane.setTopAnchor(menuBar.getView(), 0.0);
        AnchorPane.setLeftAnchor(mainView, 0.0);
        AnchorPane.setTopAnchor(mainView, MENU_BAR_HEIGHT);
        AnchorPane.setRightAnchor(mainView, 0.0);
        AnchorPane.setBottomAnchor(mainView, 0.0);
    }

    private void addElements() {
        rootPane.getChildren().addAll(menuBar.getView(), mainView);
    }

    /**
     * Add the JavaFx Node representation of a subView into the parent View in a hierarchical manner.
     *
     * @param view: A SubView object.
     */
    @Override
    public void addChild(SubView view) {
        rootPane.getChildren().add(view.getView());
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }
}
