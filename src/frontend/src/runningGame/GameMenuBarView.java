package runningGame;

import api.SubView;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * This is the menu bar for the running game window.
 *
 * @author Haotian Wang
 */
public class GameMenuBarView implements SubView<MenuBar> {
    private MenuBar menuBar;
    private GameLoader gameLoader;

    public GameMenuBarView(double height) {
        menuBar = constructMenuBar(height);
        gameLoader = new GameLoader();
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

    private void handleNewFile(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Opening a new game");
        alert.setHeaderText("You are about to open a new game player");
        alert.setContentText("All current unsaved progress will be lost. Do you want to proceed?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            menuBar.getScene().setRoot(new GameWindow().getView());
        }
    }

    private void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choosing a saved game file to play");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                gameLoader.loadGame(file);
                // TODO
            } catch (IllegalSavedGameException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something went wrong...");
                alert.setContentText("The game loader is trying to load a broken/unsupported game file");
            }
        }
    }

    private void handleSave(ActionEvent event) {
    }

    private void handleSaveAs(ActionEvent event) {
    }

    private void handleClose(ActionEvent event) {
    }

    private void handleHelp(ActionEvent event) {
    }

    private void handleAbout(ActionEvent event) {
    }

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
