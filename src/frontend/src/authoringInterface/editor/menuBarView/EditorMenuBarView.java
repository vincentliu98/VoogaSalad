package authoringInterface.editor.menuBarView;

import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.MainAuthoringProgram;
import authoringInterface.View;
import authoringInterface.editor.menuBarView.subMenuBarView.CloseFileView;
import authoringInterface.editor.memento.Editor;
import authoringInterface.editor.memento.EditorCaretaker;
import authoringInterface.editor.menuBarView.subMenuBarView.LoadFileView;
import authoringInterface.editor.menuBarView.subMenuBarView.NewWindowView;
import authoringInterface.editor.menuBarView.subMenuBarView.SaveFileView;
import graphUI.groovy.GroovyPaneFactory;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import graphUI.phase.PhasePane;
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

public class EditorMenuBarView implements SubView<MenuBar> {

    private MenuBar menuBar;
    private GameWindow gameWindow;
    private AuthoringTools authTools;
    private GroovyPaneFactory groovyPaneFactory; // hmm it's probably temporary

    private final EditorCaretaker editorCaretaker = new EditorCaretaker();
    private final Editor editor = new Editor();
    private Integer currentMemento = 0;
    private Runnable closeWindow;

    public EditorMenuBarView(AuthoringTools authTools, GroovyPaneFactory groovyPaneFactory, Runnable closeWindow) {
        this.authTools = authTools;
        this.groovyPaneFactory = groovyPaneFactory;
        editor.setState(authTools.globalData());

        this.closeWindow = closeWindow;

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
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        newFile.setOnAction(this::handleNewFile);
        open.setOnAction(this::handleOpen);
        save.setOnAction(this::handleSave);
        saveAs.setOnAction(this::handleSaveAs);
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
    private void handleGraph(ActionEvent e) {
        var newStage = new Stage();
        new PhasePane(
            newStage,
            authTools.phaseDB(),
            groovyPaneFactory.withStage(newStage)::gen
        );
    }

    void handleOpen(ActionEvent event) {
        new LoadFileView();
    }
    void handleNewFile(ActionEvent event) { new NewWindowView(); }
    void handleClose(ActionEvent event) { new CloseFileView(closeWindow); }
    void handleSave(ActionEvent event) {
        // TODO: 11/17/18 Enable and Disable the undo and redo button
        editorCaretaker.addMemento(editor.save());
        editor.setState(editorCaretaker.getMemento(currentMemento++).getSavedState());
    }
    void handleSaveAs(ActionEvent event) {
        new SaveFileView();
        handleSave(event);
    }
    void handleUndo(ActionEvent event) {
        if (currentMemento < 2) return;
        editor.restoreToState(editorCaretaker.getMemento(--currentMemento));
        // TODO: 11/17/18 Redisplay content
        // need to scan through the map and find out which ones need update
    }
    void handleRedo(ActionEvent event) {
        editor.restoreToState(editorCaretaker.getMemento(++currentMemento));
        // TODO: 11/17/18 Redisplay content
        // need to scan through the map and find out which ones need update
    }
    void handleRunProject(ActionEvent event) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Your Game");
        gameWindow = new GameWindow();
        Scene newScene = new Scene(gameWindow.getView(), View.GAME_WIDTH, View.GAME_HEIGHT);
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
