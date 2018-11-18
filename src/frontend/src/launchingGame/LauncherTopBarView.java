package launchingGame;

import api.SubView;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;



public class LauncherTopBarView implements SubView<HBox> {
    public static final String DIVIDER_PATH = "/icons/gray-line.png";
    public static final double DIVIDER_RATIO = 0.7;
    public static final double SPACING = 25;

    private HBox myBox;
    private SearchBar mySearchBar;
    private ImageView myDivider;
    private TextOptions myTextOptions;

    private double initHeight;



    public LauncherTopBarView(double height){
        initHeight = height;

        initBox();
        initDivider();

        mySearchBar = new SearchBar();

        myTextOptions = new TextOptions();

        myBox.getChildren().add(mySearchBar.getView());
        myBox.getChildren().add(myDivider);
        myBox.getChildren().add(myTextOptions.getView());
    }

    private void initBox(){
        myBox = new HBox();
        myBox.setPrefHeight(initHeight);
        myBox.getStyleClass().add("launcher-top-bar");

        myBox.setSpacing(SPACING);
        myBox.setAlignment(Pos.CENTER_LEFT);
    }

    private void initDivider(){
        Image image = new Image(getClass().getResourceAsStream(DIVIDER_PATH));
        myDivider = new ImageView(image);

        myDivider.setFitHeight(initHeight * DIVIDER_RATIO);
        myDivider.setFitWidth(2);

    }

    @Override
    public HBox getView() {
        return myBox;
    }
}
