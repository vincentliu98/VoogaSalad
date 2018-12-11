package launchingGame;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class SortIcon {
    public static final double BUTTON_WIDTH = 30;
    public static final double BUTTON_HEIGHT = 30;

    private Button myButton;
    private HBox myButtonHolder;
    private ImageView myImage;
    private Sortable mySortable;


    public SortIcon(Sortable sortable) {
        mySortable = sortable;
        initImage();
        initButton();
    }

    private void initImage() {
        Image image = new Image(getClass().getResourceAsStream("/graphics/A_icon.png"));
        myImage = new ImageView(image);
        myImage.setFitHeight(BUTTON_HEIGHT);
        myImage.setFitWidth(BUTTON_WIDTH);
    }

    private void initButton() {
        myButton = new Button();
        myButton.getStyleClass().add("sort-button");
        myButton.setPrefWidth(BUTTON_WIDTH);
        myButton.setPrefHeight(BUTTON_HEIGHT);
        myButton.setGraphic(myImage);
        myButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // myPlayButton.getStyleClass().remove(PLAYBUTTON_CSS_NORMAL);
                // myPlayButton.getStyleClass().add(PLAYBUTTON_CSS_HOVER);
            }
        });

        myButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // myPlayButton.getStyleClass().remove(PLAYBUTTON_CSS_HOVER);
                // myPlayButton.getStyleClass().add(PLAYBUTTON_CSS_NORMAL);
            }
        });
        myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //MainPlayer myPlayer = new MainPlayer();
                //myPlayer.launchGame(myReferencePath);
                mySortable.sortByAlphabet();
            }
        });

        myButtonHolder = new HBox();
        myButtonHolder.getChildren().add(myButton);
        myButtonHolder.setAlignment(Pos.TOP_CENTER);
        //myButtonHolder.getStyleClass().add(BUTTON_HOLDER_CSS);
    }

    public HBox getView() {
        return myButtonHolder;
    }
}
