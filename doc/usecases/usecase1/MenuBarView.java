package authoringInterface.menu;

import api.ParentView;
import api.SubView;
import authoringInterface.MainAuthoringProgram;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * This class represents the default menu bar at the top of the game authoring GUI.
 *
 * @author Haotian Wang
 */
public class MenuBarView implements SubView, ParentView<Menu> {
    public static final int WIDTH = 800;
    public static final int GAME_WIDTH = WIDTH;
    public static final int GAME_HEIGHT = 600;
    private static final double DEFAULT_HEIGHT = 30;
    private MenuBar menuBar;
    private Stage primaryStage;

    public MenuBarView(Stage primaryStage, double height) {
        menuBar = constructMenuBar(height);
        this.primaryStage = primaryStage;
    }

    public MenuBarView(Stage primaryStage) {
        this(primaryStage, DEFAULT_HEIGHT);
    }

    /**
     * This method constructs the menu bar.
     *
     * @return A MenuBar Node to be displayed at the top of the empty window.
     */
    private MenuBar constructMenuBar(double height) {
        MenuBar menuBar = new MenuBar();

        menuBar.setPrefHeight(height);

        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu tools = new Menu("Tools");
        Menu run = new Menu("Run");
        Menu help = new Menu("Help");

        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem close = new MenuItem("Close");
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");
        MenuItem runProject = new MenuItem("Run");
        MenuItem helpDoc = new MenuItem("Help");
        MenuItem about = new MenuItem("About");

        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        newFile.setOnAction(this::handleNewFile);
        open.setOnAction(this::handleOpen);
        save.setOnAction(this::handleSave);
        saveAs.setOnAction(this::handeSaveAs);
        close.setOnAction(this::handleClose);
        undo.setOnAction(this::handleUndo);
        redo.setOnAction(this::handleRedo);
        runProject.setOnAction(this::handleRunProject);
        helpDoc.setOnAction(this::handleHelpDoc);
        about.setOnAction(this::handleAbout);

        file.getItems().addAll(newFile, open, save, saveAs, close);
        edit.getItems().addAll(undo, redo);
        run.getItems().addAll(runProject);
        help.getItems().addAll(helpDoc, about);

        menuBar.getMenus().addAll(file, edit, tools, run, help);
        return menuBar;
    }

    void handleNewFile(ActionEvent event) {
    }

    void handleOpen(ActionEvent event) {
    }

    void handleSave(ActionEvent event) {
    }

    void handeSaveAs(ActionEvent event) {
    }

    void handleClose(ActionEvent event) {
    }

    void handleUndo(ActionEvent event) {
    }

    void handleRedo(ActionEvent event) {
    }

    void handleRunProject(ActionEvent event) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Your Game");
        Group group = new Group();
        Scene newScene = new Scene(group, GAME_WIDTH, GAME_HEIGHT);
        newWindow.setScene(newScene);
        newWindow.setX(MainAuthoringProgram.SCREEN_WIDTH * 0.5 - GAME_WIDTH * 0.5);
        newWindow.setY(MainAuthoringProgram.SCREEN_HEIGHT * 0.5 - GAME_HEIGHT * 0.5);
        newWindow.show();
    }

    void handleHelpDoc(ActionEvent event) {
    }

    void handleAbout(ActionEvent event) {
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public Node getView() {
        return menuBar;
    }

    /**
     * Add the JavaFx Node representation of a subView into the parent View in a hierarchical manner.
     *
     * @param menu : A Menu object.
     */
    @Override
    public void addChild(Menu menu) {
        menuBar.getMenus().add(menu);
    }
}
