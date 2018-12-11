package authoringInterface;

import api.ParentView;
import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.editor.editView.EditView;
import authoringInterface.editor.menuBarView.EditorMenuBarView;
import authoringInterface.sidebar.SideView;
import authoringInterface.sidebar.StatusView;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.tileGeneration.TileGenerator;
import graphUI.groovy.GroovyPaneFactory;
import grids.Point;
import grids.PointImpl;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import utils.exception.XMLParsingException;
import utils.nodeInstance.CrappyNodeInstanceController;
import utils.nodeInstance.NodeInstanceController;
import utils.serializer.CRUDLoadException;
import utils.serializer.XMLParser;

import java.io.File;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * This class provides an createPhaseGraph skeleton window with the basic menu items, and basic editing interfaces.
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
    private static final String DEFAULT_CONFIG = Objects.requireNonNull(View.class.getClassLoader().getResource("default.xml")).getFile();
    private NodeInstanceController nodeInstanceController;
    private GameObjectsCRUDInterface gameObjectManager;
    public static final double MENU_BAR_HEIGHT = 30;
    public static final double GAME_WIDTH = 700;
    public static final double GAME_HEIGHT = 500;
    private static final int ROW_NUMBER = 10;
    private static final int COL_NUMBER = 7;
    private static final double SIDEBAR_WIDTH = 250;
    private XMLParser xmlParser;

    /**
     * Constructor for an createPhaseGraph window, with an AnchorPane as the root Node, and the AnchorPane constraints on top, left and right are 0.
     */
    public View(Stage primaryStage) { this(primaryStage, new AuthoringTools(COL_NUMBER, ROW_NUMBER)); }
    public View(Stage primaryStage, String xml) { this(primaryStage, new AuthoringTools(xml)); }
    public View(Stage primaryStage, AuthoringTools authTools) {
        this.primaryStage = primaryStage;
        xmlParser = new XMLParser();
        try { xmlParser.loadXML(new File(DEFAULT_CONFIG)); } catch (SAXException | CRUDLoadException | XMLParsingException ignored) {}
        mainView = new GridPane();
        rootPane = new AnchorPane();
        rootPane.getStyleClass().add("mainPane");
        tools = authTools;

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
        editView = new EditView(tools, groovyPaneFactory, gameObjectManager.getNumRows(), gameObjectManager.getNumCols(), gameObjectManager, nodeInstanceController);
        statusView = new StatusView(gameObjectManager);
        editView.addUpdateStatusEventListener(statusView);
        menuBar = new EditorMenuBarView(tools, primaryStage::close, this::updateGridDimension, editView, gameObjectManager, primaryStage);
        sidebar.addColumn(0, sideView.getView(), statusView.getView());
        mainView.getColumnConstraints().addAll(new ColumnConstraints(MainAuthoringProgram.SCREEN_WIDTH - SIDEBAR_WIDTH));
        mainView.addColumn(0, editView.getView());
        mainView.addColumn(1, sidebar);
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
