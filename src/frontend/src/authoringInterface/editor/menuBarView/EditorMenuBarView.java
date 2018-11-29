package authoringInterface.editor.menuBarView;

import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.MainAuthoringProgram;
import authoringInterface.View;
import authoringInterface.editor.menuBarView.subMenuBarView.CloseFileView;
import authoringInterface.editor.memento.Editor;
import authoringInterface.editor.memento.EditorCaretaker;
import authoringInterface.editor.menuBarView.subMenuBarView.LoadFileView;
import gameplay.Initializer;
import authoringInterface.editor.menuBarView.subMenuBarView.NewWindowView;
import authoringInterface.editor.menuBarView.subMenuBarView.SaveFileView;
import graphUI.groovy.GroovyPaneFactory;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import runningGame.GameWindow;
import java.io.File;
/**
 * MenuBarView class
 *
 * @author Haotian
 * @author Amy
 */
public class EditorMenuBarView implements SubView<MenuBar> {

    private MenuBar menuBar;
    private GameWindow gameWindow;
    private AuthoringTools authTools;

    private final EditorCaretaker editorCaretaker = new EditorCaretaker();
    private final Editor editor = new Editor();
    private Integer currentMemento = 0;
    private Runnable closeWindow; //For each window closable

    public EditorMenuBarView(AuthoringTools authTools, Runnable closeWindow) {
        this.authTools = authTools;

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

        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        newFile.setOnAction(e -> new NewWindowView());
        open.setOnAction(e -> new LoadFileView());
        save.setOnAction(this::handleSave);
        saveAs.setOnAction(this::handleSaveAs);
        close.setOnAction(e -> new CloseFileView(closeWindow));
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
    }

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
        try{
            Initializer initializer =
                    new Initializer(new File(getClass().getClassLoader().getResource("SwordAndArrow.xml").getFile()));
            Scene newScene = new Scene(initializer.getRoot(), View.GAME_WIDTH, View.GAME_HEIGHT);
            newWindow.setScene(newScene);
            newWindow.setX(MainAuthoringProgram.SCREEN_WIDTH*0.5 - View.GAME_WIDTH*0.5);
            newWindow.setY(MainAuthoringProgram.SCREEN_HEIGHT*0.5 - View.GAME_HEIGHT*0.5);
            initializer.setScreenSize(View.GAME_WIDTH, View.GAME_HEIGHT);
            newWindow.show();
        } catch (Exception e){
            e.printStackTrace();
        }
//        Scene newScene = new Scene(gameWindow.getView(), View.GAME_WIDTH, View.GAME_HEIGHT);

    }
    void handleHelpDoc(ActionEvent event) {}
    void handleAbout(ActionEvent event) {}

    @Override
    public MenuBar getView() {
        return menuBar;
    }
}
