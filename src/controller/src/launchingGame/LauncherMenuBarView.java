package launchingGame;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import runningGame.GameLoader;

public class LauncherMenuBarView {

    private MenuBar menuBar;
    private GameLoader gameLoader;

    public LauncherMenuBarView(double height) {
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

        Menu LaunchGame = new Menu("Launch Game");

        // MenuItem newFile = new MenuItem("New");

//        newFile.setOnAction(this::handleNewFile);
//
//        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
//
//        LaunchGame.getItems().addAll(newFile, open, save, saveAs, close);
//        help.getItems().addAll(helpDoc, about);

        menuBar.getMenus().addAll(LaunchGame);

        menuBar.setPrefHeight(height);

        return menuBar;
    }

//    private void handleNewFile(ActionEvent event) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Opening a new game");
//        alert.setHeaderText("You are about to open a new game player");
//        alert.setContentText("All current unsaved progress will be lost. Do you want to proceed?");
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK) {
//            menuBar.getScene().setRoot(new GameWindow().getView());
//        }
//    }

//    private void handleOpen(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Choosing a saved game file to play");
//        File file = fileChooser.showOpenDialog(new Stage());
//        if (file != null) {
//            try {
//                gameLoader.loadGame(file);
//            } catch (IllegalSavedGameException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Something went wrong...");
//                alert.setContentText("The game loader is trying to load a broken/unsupported game file");
//            }
//        }
//    }

//    private void handleSave(ActionEvent event) {}
//
//    private void handleSaveAs(ActionEvent event) {}
//
//    private void handleClose(ActionEvent event) {}
//
//    private void handleHelp(ActionEvent event) {}
//
//    private void handleAbout(ActionEvent event) {}


    public MenuBar getView() {
        return menuBar;
    }

}
