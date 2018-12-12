package launchingGame;

import javafx.scene.layout.VBox;

public class LauncherSideBarView {
    private VBox myVBox;

    public LauncherSideBarView(double width, Sortable sortable) {
        myVBox = new VBox();


        myVBox.setPrefWidth(width);
        myVBox.getStyleClass().add("launcher-side-bar");

        SortIcon srt = new SortIcon(sortable);

        myVBox.getChildren().add(srt.getView());

    }

    public VBox getView() {
        return myVBox;
    }
}
