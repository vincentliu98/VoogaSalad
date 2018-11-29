package launchingGame;

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
import playing.MainPlayer;

public class GameIcon {
    public static final String TEXT_CSS = "title-box";
    public static final String DESCRIPTION_CSS = "description-box";
    public static final double DESCRIPTION_INSET = 10;
    public static final String PLAYBUTTON_TEXT = "Play";
    public static final String PLAYBUTTON_CSS_NORMAL = "play-button-normal";
    public static final double PLAYBUTTON_WIDTH = 110;
    public static final double PLAYBUTTON_HEIGHT = 35;
    public static final String PLAYBUTTON_CSS_HOVER = "play-button-hover";
    public static final String BUTTON_HOLDER_CSS = "button-holder";
    public static final double ICON_WIDTH = 250;
    public static final double ICON_HEIGHT = 180;
    public static final String IMAGES_FOLDER_PATH = "/game-images/";
    public static final String SPACE_REGEX = "[ \\t]+";

    private StackPane myPane;
    private ImageView myBackground;
    private HBox myTitleHolder;
    private Text myTitle;
    private HBox myDescriptionHolder;
    private Text myDescription;
    private Button myPlayButton;
    private HBox myButtonHolder;

    private String myName;
    private String myDescriptionString;
    private String myImagePath;
    private String[] myTags;
    private String myReferencePath;


    public GameIcon(String gameName, String description, String reference, String color, String imagePath, String tags){
        myName = gameName;
        myDescriptionString = description;
        myReferencePath = reference;
        myImagePath = imagePath;
        myTags = tags.split(SPACE_REGEX);

        initPane();
        initBackground();
        initTitle();
        initDescription();
        initButton();
    }

    public Boolean checkTag(String tag){
        if(myName.equals(tag)){
            return true;
        }
        for(String tg: myTags){
            if(tg.equals(tag)){
                return true;
            }
        }
        return false;
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

    private void initBackground(){
        Image image = new Image(getClass().getResourceAsStream(IMAGES_FOLDER_PATH + myImagePath));
        myBackground = new ImageView(image);
        myBackground.setFitWidth(ICON_WIDTH);
        myBackground.setFitHeight(ICON_HEIGHT);
        myPane.getChildren().add(myBackground);
    }

    private void initTitle(){
        myTitle = new Text(myName);
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

        myDescription = new Text(myDescriptionString);
        myDescription.setTextAlignment(TextAlignment.LEFT);
        myDescription.setFill(Color.BLACK);
        myDescription.setWrappingWidth(ICON_WIDTH - DESCRIPTION_INSET);


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
        myPlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MainPlayer myPlayer = new MainPlayer();
                myPlayer.launchGame(myReferencePath);
            }
        });

        myButtonHolder = new HBox();
        myButtonHolder.getChildren().add(myPlayButton);
        myButtonHolder.setAlignment(Pos.BOTTOM_LEFT);
        myButtonHolder.getStyleClass().add(BUTTON_HOLDER_CSS);
    }

    public String getName(){
        return myName;
    }

    public StackPane getView() {
        return myPane;
    }
}
