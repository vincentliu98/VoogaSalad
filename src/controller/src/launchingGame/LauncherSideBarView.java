package launchingGame;

import javafx.scene.layout.VBox;

public class LauncherSideBarView {
    private VBox myVBox;

    public LauncherSideBarView(double width){
        myVBox = new VBox();


        myVBox.setPrefWidth(width);
        myVBox.getStyleClass().add("launcher-side-bar");

    }

    public VBox getView() {
        return myVBox;
    }
}
