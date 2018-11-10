package authoringInterface;

import api.SubView;
import authoringInterface.editor.EditView;
import authoringInterface.editor.EmptySkeleton;
import authoringInterface.sidebar.SideView;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;

/**
 * This class creates the default window for the authoring engine, built upon the EmptySkeleton.
 *
 * @author Haotian Wang
 */
public class DefaultWindow implements SubView<Parent> {
    private static final double MENU_BAR_HEIGHT = 30;
    private EmptySkeleton emptySkeleton;
    private MenuBar menuBar;
    private SideView sideView;
    private EditView editView;

    public DefaultWindow() {
        emptySkeleton = new EmptySkeleton();
        menuBar = constructMenuBar(MENU_BAR_HEIGHT);
        sideView = new SideView();
        editView = new EditView();
        setElements();
        addElements();
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

    void handleNewFile(ActionEvent event) {}
    void handleOpen(ActionEvent event) {}
    void handleSave(ActionEvent event) {}
    void handeSaveAs(ActionEvent event) {}
    void handleClose(ActionEvent event) {}
    void handleUndo(ActionEvent event) {}
    void handleRedo(ActionEvent event) {}
    void handleRunProject(ActionEvent event) {}
    void handleHelpDoc(ActionEvent event) {}
    void handleAbout(ActionEvent event) {}

    private void setElements() {
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);
        AnchorPane.setTopAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(sideView.getView(), 0.0);
        AnchorPane.setTopAnchor(sideView.getView(), 30.0);
        AnchorPane.setBottomAnchor(sideView.getView(), 0.0);
        AnchorPane.setLeftAnchor(editView.getView(), 0.0);
        AnchorPane.setRightAnchor(editView.getView(), 247.9);
        AnchorPane.setTopAnchor(editView.getView(), 30.0);
        AnchorPane.setBottomAnchor(editView.getView(), 0.0);
    }

    private void addElements() {
        emptySkeleton.getView().getChildren().add(menuBar);
        emptySkeleton.addChild(sideView);
        emptySkeleton.addChild(editView);
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public Parent getView() {
        return (Parent) emptySkeleton.getView();
    }
}
