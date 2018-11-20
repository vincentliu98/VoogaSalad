package launchingGame;

import api.SubView;
import javafx.scene.layout.BorderPane;

public class LauncherWindow implements SubView<BorderPane> {
    public static final double TOP_HEIGHT = 65;
    public static final double SIDE_WIDTH = 50;

    private BorderPane myPane;

    public LauncherWindow(){
        myPane = new BorderPane();


        LauncherTopBarView myBar = new LauncherTopBarView(TOP_HEIGHT);

        LauncherSideBarView mySide = new LauncherSideBarView(SIDE_WIDTH);

        LauncherGamesDisplay myDisplay = new LauncherGamesDisplay();

        myPane.setTop(myBar.getView());
        myPane.setLeft(mySide.getView());
        myPane.setCenter(myDisplay.getView());
    }



    @Override
    public BorderPane getView() {
        return myPane;
    }
}
