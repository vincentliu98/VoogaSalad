package runningGame;

import api.SubView;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * This is the menu bar for the running game window.
 *
 * @author Haotian Wang
 */
public class GameMenuBarView implements SubView<MenuBar> {
    private MenuBar menuBar;

    public GameMenuBarView(double height) {
        menuBar = constructMenuBar(height);

    }

    /**
     * This method constructs a default menu for the gaming window.
     *
     * @return A MenuBar JavaFx item.
     */
    private MenuBar constructMenuBar(double height) {
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("File");
        Menu help = new Menu("Help");

        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem close = new MenuItem("Close");
        MenuItem helpDoc = new MenuItem("Help");
        MenuItem about = new MenuItem("About");

        newFile.setOnAction(this::handleNewFile);
        open.setOnAction(this::handleOpen);
        save.setOnAction(this::handleSave);
        saveAs.setOnAction(this::handleSaveAs);
        close.setOnAction(this::handleClose);
        helpDoc.setOnAction(this::handleClose);
        about.setOnAction(this::handleAbout);

        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        file.getItems().addAll(newFile, open, save, saveAs, close);
        help.getItems().addAll(helpDoc, about);

        menuBar.getMenus().addAll(file, help);

        menuBar.setPrefHeight(height);

        return menuBar;
    }

    private void handleNewFile(ActionEvent event) {}

    private void handleOpen(ActionEvent event) {}

    private void handleSave(ActionEvent event) {}

    private void handleSaveAs(ActionEvent event) {}

    private void handleClose(ActionEvent event) {}

    private void handleHelp(ActionEvent event) {}

    private void handleAbout(ActionEvent event) {}

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public MenuBar getView() {
        return menuBar;
    }
}
