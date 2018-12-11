package authoringInterface.editor.menuBarView;

import api.SubView;
import authoring.AuthoringTools;
import authoringInterface.View;
import authoringInterface.editor.editView.EditView;
import authoringInterface.editor.menuBarView.subMenuBarView.*;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.tileGeneration.TileGenerator;
import gameplay.Initializer;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import runningGame.GameWindow;
import utils.ErrorWindow;
import conversion.authoring.SerializerCRUD;

import java.io.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static authoringInterface.MainAuthoringProgram.SCREEN_HEIGHT;
import static authoringInterface.MainAuthoringProgram.SCREEN_WIDTH;
import static authoringInterface.editor.menuBarView.subMenuBarView.NewWindowView.STYLESHEET;

/**
 * MenuBarView class
 *
 * @author Haotian
 * @author Amy
 * @author jl729
 */
public class EditorMenuBarView implements SubView<MenuBar> {
    private EditView editView;
    private MenuBar menuBar;
    private GameWindow gameWindow;
    private AuthoringTools authTools;
    private String fileName; //TODO: temp var, will be changed
    private SerializerCRUD serializer;
    private SoundView soundView;
    private Runnable closeWindow; //For each window closable
    private TileGenerator tileGenerator;
    private GameObjectsCRUDInterface gameObjectManager;

    private File currentFile;
    private Stage primaryStage;

    public EditorMenuBarView(
            AuthoringTools authTools,
            Runnable closeWindow,
            BiConsumer<Integer, Integer> updateGridDimension,
            EditView editView,
            GameObjectsCRUDInterface gameObjectManager,
            Stage primaryStage
    ) {
        serializer = new SerializerCRUD();
        this.authTools = authTools;
        this.closeWindow = closeWindow;
        this.editView = editView;
        this.primaryStage = primaryStage;
        this.gameObjectManager = gameObjectManager;
        fileName = "TicTacToe.xml";

        menuBar = new MenuBar();
        menuBar.setPrefHeight(View.MENU_BAR_HEIGHT);

        soundView = new SoundView();

        Menu file = new Menu("File");
        Menu settings = new Menu("Settings");
        Menu run = new Menu("Run");
        Menu help = new Menu("Help");

        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem export = new MenuItem("Export");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem close = new MenuItem("Close");
        MenuItem runProject = new MenuItem("Run");
        MenuItem resizeGrid = new MenuItem("Resize Grid");
        MenuItem setBGM = new MenuItem("BGM");
        MenuItem helpDoc = new MenuItem("Help");
        MenuItem about = new MenuItem("About");
        MenuItem tileSetting = new MenuItem("Tile Setting");

        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        resizeGrid.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        helpDoc.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));

        newFile.setOnAction(e -> new NewWindowView());
        open.setOnAction(this::handleOpen);
        export.setOnAction(this::handleExport);

        save.setOnAction(this::handleSave);
        saveAs.setOnAction(this::handleSaveAs);
        close.setOnAction(e -> new CloseFileView(closeWindow));
        runProject.setOnAction(this::handleRunProject);
        resizeGrid.setOnAction(e -> new ResizeGridView().showAndWait().ifPresent(dimension ->
                updateGridDimension.accept(dimension.getKey(), dimension.getValue())
        ));
        setBGM.setOnAction(e -> soundView.show());
        helpDoc.setOnAction(this::handleHelpDoc);
        about.setOnAction(this::handleAbout);
        tileSetting.setOnAction(this::handleTileSetting);

        file.getItems().addAll(newFile, open, export, save, saveAs, close);
        run.getItems().addAll(runProject);
        settings.getItems().addAll(resizeGrid, setBGM, tileSetting);
        help.getItems().addAll(helpDoc, about);

        menuBar.getMenus().addAll(file, settings, run, help);
    }

    void handleTileSetting(ActionEvent actionEvent) {
        var tileSettingWindow = new TileSettingDialog(gameObjectManager, primaryStage);
        tileSettingWindow.showWindow();
    }


    void handleSave(ActionEvent event) {
        if(currentFile == null) handleSaveAs(event);
        currentFile.delete();
        writeToFile(currentFile);
    }

    void handleSaveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project files");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) writeToFile(file);
        else ErrorWindow.display("Error", "Empty File");
    }

    void writeToFile(File file) {
        try {
            var writer = new BufferedWriter(new FileWriter(file));
            writer.write(authTools.toAuthoringXML());
            currentFile = file;
            writer.close();
        } catch (IOException e) {
            ErrorWindow.display("Error", "Something went wrong when writing to the file");
            e.printStackTrace();
        }
    }

    void handleExport(ActionEvent event) { new SaveFileView(authTools::toEngineXML); }

    void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project files");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                Stage newWindow = new Stage();
                newWindow.setTitle("VoogaSalad!");
                var reader = new BufferedReader(new FileReader(file));
                String xml = reader.lines().reduce((s1, s2) -> s1+"\n"+s2).get();
                View myView = new View(newWindow, xml);
                Scene newScene = new Scene(myView.getRootPane(), SCREEN_WIDTH, SCREEN_HEIGHT);
                newScene.getStylesheets().add(STYLESHEET);
                newWindow.setScene(newScene);
                newWindow.show();
                reader.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    void handleRunProject(ActionEvent event) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Your Game");
        gameWindow = new GameWindow();
        try{
            Initializer initializer =
                new Initializer(new File(getClass().getClassLoader().getResource(fileName).getFile()));
            Scene newScene = new Scene(initializer.getRoot(), View.GAME_WIDTH, View.GAME_HEIGHT);
            newScene.addEventFilter(KeyEvent.KEY_RELEASED, initializer::keyFilter);
            newWindow.setScene(newScene);
            newWindow.setX(SCREEN_WIDTH*0.5 - View.GAME_WIDTH*0.5);
            newWindow.setY(SCREEN_HEIGHT*0.5 - View.GAME_HEIGHT*0.5);
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
