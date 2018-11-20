package launchingGame;

import api.SubView;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class GameIcon implements SubView<StackPane> {
    public static final String TEXT_CSS = "title-box";
    public static final String DESCRIPTION_CSS = "description-box";
    public static final double DESCRIPTION_INSET = 10;
    public static final String PLAYBUTTON_TEXT = "Play";
    public static final String PLAYBUTTON_CSS_NORMAL = "play-button-normal";
    public static final double PLAYBUTTON_WIDTH = 110;
    public static final double PLAYBUTTON_HEIGHT = 35;
    public static final String PLAYBUTTON_CSS_HOVER = "play-button-hover";
    public static final String BUTTON_HOLDER_CSS = "button-holder";

    private StackPane myPane;
    private ImageView myBackground;
    private HBox myTitleHolder;
    private Text myTitle;
    private HBox myDescriptionHolder;
    private Text myDescription;
    private Button myPlayButton;
    private HBox myButtonHolder;

    private double myWidth;

    public GameIcon(double width, double height){
        myWidth = width;
        initPane();
        initBackground(width, height);
        initTitle();
        initDescription();
        initButton();
    }

    private void initPane(){
        myPane = new StackPane();

        myPane.setOnMouseEntered(event -> {
            myPane.getChildren().remove(myTitleHolder);
            myPane.getChildren().add(myDescriptionHolder);
            myPane.getChildren().add(myButtonHolder);
        });

        myPane.setOnMouseExited(event -> {
            myPane.getChildren().remove(myDescriptionHolder);
            myPane.getChildren().remove(myButtonHolder);
            myPane.getChildren().add(myTitleHolder);
        });

    }

    private void initBackground(double width, double height){
        Image image = new Image(getClass().getResourceAsStream("/images/background.jpg"));
        myBackground = new ImageView(image);
        myBackground.setFitWidth(width);
        myBackground.setFitHeight(height);
        myPane.getChildren().add(myBackground);
    }

    private void initTitle(){
        myTitle = new Text("Chess");
        myTitle.setFill(Color.BLACK);

        myTitleHolder = new HBox();
        myTitleHolder.getChildren().add(myTitle);
        myTitleHolder.setAlignment(Pos.BOTTOM_LEFT);
        myTitleHolder.getStyleClass().add(TEXT_CSS);

        myPane.getChildren().add(myTitleHolder);
    }

    private void initDescription(){
        myDescriptionHolder = new HBox();
        myDescriptionHolder.setAlignment(Pos.CENTER);
        myDescriptionHolder.getStyleClass().add(DESCRIPTION_CSS);

        myDescription = new Text("Info: Released in 1976, Omen was directed by Richard Donner and written by David Seltzer. It stars Gregory Peck and Lee Remick. The film was a major critical and financial success and was released originally by 20th Century Fox in the UK and the US. ");
        myDescription.setTextAlignment(TextAlignment.LEFT);
        myDescription.setFill(Color.BLACK);
        myDescription.setWrappingWidth(myWidth - DESCRIPTION_INSET);


        myDescriptionHolder.getChildren().add(myDescription);

    }

    private void initButton(){
        myPlayButton = new Button(PLAYBUTTON_TEXT);
        myPlayButton.setTextFill(Color.WHITE);
        myPlayButton.getStyleClass().add(PLAYBUTTON_CSS_NORMAL);
        myPlayButton.setPrefWidth(PLAYBUTTON_WIDTH);
        myPlayButton.setPrefHeight(PLAYBUTTON_HEIGHT);
        myPlayButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myPlayButton.getStyleClass().remove(PLAYBUTTON_CSS_NORMAL);
                myPlayButton.getStyleClass().add(PLAYBUTTON_CSS_HOVER);
            }
        });

        myPlayButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myPlayButton.getStyleClass().remove(PLAYBUTTON_CSS_HOVER);
                myPlayButton.getStyleClass().add(PLAYBUTTON_CSS_NORMAL);
            }
        });

        myButtonHolder = new HBox();
        myButtonHolder.getChildren().add(myPlayButton);
        myButtonHolder.setAlignment(Pos.BOTTOM_LEFT);
        myButtonHolder.getStyleClass().add(BUTTON_HOLDER_CSS);
    }

    @Override
    public StackPane getView() {
        return myPane;
    }
}
