package launchingGame;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LauncherWindow {
    public static final double TOP_HEIGHT = 65;
    public static final double SIDE_WIDTH = 50;

    private BorderPane myPane;
    private Stage myStage;

    private LauncherTopBarView myBar;
    private LauncherSideBarView mySide;
    private LauncherGamesDisplay myGameDisplay;

    public LauncherWindow(Stage stage) {
        myPane = new BorderPane();
        myStage = stage;

        myGameDisplay = new LauncherGamesDisplay();
        myBar = new LauncherTopBarView(TOP_HEIGHT, myStage, myPane, myGameDisplay);
        mySide = new LauncherSideBarView(SIDE_WIDTH, myGameDisplay);

        myPane.setTop(myBar.getView());
        myPane.setLeft(mySide.getView());
        myPane.setCenter(myGameDisplay.getView());
    }

    public BorderPane getView() {
        return myPane;
    }
}
