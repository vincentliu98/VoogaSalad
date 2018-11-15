package authoringInterface.editor;

import api.SubView;
import authoringInterface.MainAuthoringProgram;
import authoringInterface.View;
import graphUI.EdgeSettingWindow;
import graphUI.GraphPane;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import runningGame.GameWindow;

public class MenuBarView implements SubView<MenuBar> {

    private MenuBar menuBar;
    private GameWindow gameWindow;

    public MenuBarView() {
        menuBar = new MenuBar();
        menuBar.setPrefHeight(View.MENU_BAR_HEIGHT);

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
        MenuItem graph = new MenuItem("Graph");

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
        graph.setOnAction(this::handleGraph);

        file.getItems().addAll(newFile, open, save, saveAs, close);
        edit.getItems().addAll(undo, redo, graph);
        run.getItems().addAll(runProject);
        help.getItems().addAll(helpDoc, about);

        menuBar.getMenus().addAll(file, edit, tools, run, help);
    }

    private void handleGraph(ActionEvent actionEvent) {
        new GraphPane(new Stage());
    }


    void handleNewFile(ActionEvent event) {}
    void handleOpen(ActionEvent event) {}
    void handleSave(ActionEvent event) {}
    void handeSaveAs(ActionEvent event) {}
    void handleClose(ActionEvent event) {}
    void handleUndo(ActionEvent event) {}
    void handleRedo(ActionEvent event) {}
    void handleRunProject(ActionEvent event) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Your Game");
        gameWindow = new GameWindow();
        Scene newScene = new Scene(gameWindow, View.GAME_WIDTH, View.GAME_HEIGHT);
        newWindow.setScene(newScene);
        newWindow.setX(MainAuthoringProgram.SCREEN_WIDTH*0.5 - View.GAME_WIDTH*0.5);
        newWindow.setY(MainAuthoringProgram.SCREEN_HEIGHT*0.5 - View.GAME_HEIGHT*0.5);
        newWindow.show();
    }
    void handleHelpDoc(ActionEvent event) {}
    void handleAbout(ActionEvent event) {}

    @Override
    public MenuBar getView() {
        return menuBar;
    }
}
