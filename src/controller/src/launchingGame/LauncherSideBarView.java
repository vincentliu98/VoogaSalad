package launchingGame;

import api.SubView;
import javafx.scene.layout.VBox;

public class LauncherSideBarView implements SubView <VBox> {
    private VBox myVBox;

    public LauncherSideBarView(double width){
        myVBox = new VBox();


        myVBox.setPrefWidth(width);
        myVBox.getStyleClass().add("launcher-side-bar");

    }

    @Override
    public VBox getView() {
        return myVBox;
    }
}
