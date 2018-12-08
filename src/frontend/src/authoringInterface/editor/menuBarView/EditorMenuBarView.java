package authoringInterface.editor.menuBarView;

import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.MainAuthoringProgram;
import authoringInterface.View;
import authoringInterface.editor.memento.Editor;
import authoringInterface.editor.memento.EditorCaretaker;
import authoringInterface.editor.menuBarView.subMenuBarView.*;
import gameplay.Initializer;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import runningGame.GameWindow;

import java.io.File;
import java.util.function.BiConsumer;

/**
 * MenuBarView class
 *
 * @author Haotian
 * @author Amy
 * @author jl729
 */
public class EditorMenuBarView implements SubView<MenuBar> {
    private MenuBar menuBar;
    private GameWindow gameWindow;
    private AuthoringTools authTools;
    private String fileName; //TODO: temp var, will be changed

    private SoundView soundView;

    private final EditorCaretaker editorCaretaker = new EditorCaretaker();
    private final Editor editor = new Editor();
    private Integer currentMemento = 0;
    private Runnable closeWindow; //For each window closable

    public EditorMenuBarView(
            AuthoringTools authTools,
            Runnable closeWindow,
            BiConsumer<Integer, Integer> updateGridDimension
    ) {
        this.authTools = authTools;
        this.closeWindow = closeWindow;
        fileName = "TicTacToe.xml";

        menuBar = new MenuBar();
        menuBar.setPrefHeight(View.MENU_BAR_HEIGHT);

        soundView = new SoundView();

        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu settings = new Menu("Settings");
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
        MenuItem setPlayer = new MenuItem("Players");
        MenuItem resizeGrid = new MenuItem("Resize Grid");
        MenuItem setBGM = new MenuItem("BGM");
        MenuItem helpDoc = new MenuItem("Help");
        MenuItem about = new MenuItem("About");

        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        resizeGrid.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        helpDoc.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));

        newFile.setOnAction(e -> new NewWindowView());
        open.setOnAction(this::handleOpen);
        save.setOnAction(this::handleSave);
        saveAs.setOnAction(this::handleSaveAs);
        close.setOnAction(e -> new CloseFileView(closeWindow));
        undo.setOnAction(this::handleUndo);
        redo.setOnAction(this::handleRedo);
        runProject.setOnAction(this::handleRunProject);
        setPlayer.setOnAction(e -> new PlayerView().showAndwait());
        resizeGrid.setOnAction(e -> new ResizeGridView().showAndWait().ifPresent(dimension ->
                updateGridDimension.accept(dimension.getKey(), dimension.getValue())
        ));
        setBGM.setOnAction(e -> soundView.show());
        helpDoc.setOnAction(this::handleHelpDoc);
        about.setOnAction(this::handleAbout);

        file.getItems().addAll(newFile, open, save, saveAs, close);
        edit.getItems().addAll(undo, redo);
        run.getItems().addAll(runProject);
        settings.getItems().addAll(resizeGrid, setPlayer, setBGM);
        help.getItems().addAll(helpDoc, about);

        menuBar.getMenus().addAll(file, edit, settings, run, help);
    }

    void handleSave(ActionEvent event) {
        // TODO: 11/17/18 Enable and Disable the undo and redo button (handleUndo + handleRedo function)
        editorCaretaker.addMemento(editor.save());
        editor.setState(editorCaretaker.getMemento(currentMemento++).getSavedState());
    }
    void handleSaveAs(ActionEvent event) {
        new SaveFileView();
        handleSave(event);
    }

    void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project files");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            fileName = file.getName();
        }
    }

    void handleUndo(ActionEvent event) {
        if (currentMemento < 2) return;
        editor.restoreToState(editorCaretaker.getMemento(--currentMemento));
        // need to scan through the map and find out which ones need update
    }
    void handleRedo(ActionEvent event) {
        editor.restoreToState(editorCaretaker.getMemento(++currentMemento));
        // need to scan through the map and find out which ones need update
    }
    void handleRunProject(ActionEvent event) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Your Game");
        gameWindow = new GameWindow();
        try{
            Initializer initializer =
                    new Initializer(new File(getClass().getClassLoader().getResource(fileName).getFile()));
            Scene newScene = new Scene(initializer.getRoot(), View.GAME_WIDTH, View.GAME_HEIGHT);
            newWindow.setScene(newScene);
            newWindow.setX(MainAuthoringProgram.SCREEN_WIDTH*0.5 - View.GAME_WIDTH*0.5);
            newWindow.setY(MainAuthoringProgram.SCREEN_HEIGHT*0.5 - View.GAME_HEIGHT*0.5);
            initializer.setScreenSize(View.GAME_WIDTH, View.GAME_HEIGHT);
            newWindow.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    void handleHelpDoc(ActionEvent event) {}
    void handleAbout(ActionEvent event) {}

    @Override
    public MenuBar getView() {
        return menuBar;
    }
}
