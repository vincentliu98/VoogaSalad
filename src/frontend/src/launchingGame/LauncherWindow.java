package launchingGame;

import api.SubView;
import javafx.scene.layout.BorderPane;

public class LauncherWindow implements SubView<BorderPane> {
    BorderPane myPane;

    public LauncherWindow(){
        myPane = new BorderPane();

        LauncherMenuBarView myBar = new LauncherMenuBarView(30);

        myPane.setTop(myBar.getView());
    }



    @Override
    public BorderPane getView() {
        return myPane;
    }
}
