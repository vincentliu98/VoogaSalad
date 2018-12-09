package social;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class UserIcon implements Icon {
    public static final String TEXT_CSS = "title-box";
    public static final String ADD_FRIEND_TEXT = "Add";
    public static final String REMOVE_FRIEND_TEXT = "Remove";
    public static final String FRIEND_BUTTON_CSS_NORMAL = "play-button-normal";
    public static final double FRIEND_BUTTON_WIDTH = 110;
    public static final double FRIEND_BUTTON_HEIGHT = 35;
    public static final String FRIEND_BUTTON_CSS_HOVER = "play-button-hover";
    public static final String BUTTON_HOLDER_CSS = "button-holder";
    public static final double ICON_WIDTH = 250;
    public static final double ICON_HEIGHT = 180;

    private StackPane myPane;
    private ImageView myBackground;
    private HBox myTitleHolder;
    private Text myTitle;
    private boolean isFriended;
    private Button myFriendButton;
    private HBox myButtonHolder;
    private String myName;


    public UserIcon(String name, ImageView avatar, boolean friended){
        myName = name;
        myBackground = avatar;
        isFriended = friended;
        initPane();
        initBackground();
        initTitle();
        initButton();
    }

    private void initPane(){
        myPane = new StackPane();
    }

    @Override
    public void initBackground(){
        myBackground.setFitWidth(ICON_WIDTH);
        myBackground.setFitHeight(ICON_HEIGHT);
        myPane.getChildren().add(myBackground);
    }

    @Override
    public void initTitle(){
        myTitle = new Text(myName);
        myTitle.setFill(Color.BLACK);
        myTitleHolder = new HBox();
        myTitleHolder.getChildren().add(myTitle);
        myTitleHolder.setAlignment(Pos.BOTTOM_LEFT);
        myTitleHolder.getStyleClass().add(TEXT_CSS);
        myPane.getChildren().add(myTitleHolder);
    }

    private void initButton(){
        isFriended = false;
        myFriendButton = new Button();
        myFriendButton.setTextFill(Color.WHITE);
        myFriendButton.setPrefWidth(FRIEND_BUTTON_WIDTH);
        myFriendButton.setPrefHeight(FRIEND_BUTTON_HEIGHT);
        changeButtonDisplay();
        myFriendButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isFriended = !isFriended;
                changeButtonDisplay();
            }
        });
        myButtonHolder = new HBox();
        myButtonHolder.getChildren().add(myFriendButton);
        myButtonHolder.setAlignment(Pos.BOTTOM_LEFT);
        myButtonHolder.getStyleClass().add(BUTTON_HOLDER_CSS);
    }

    private void changeButtonDisplay(){
        if (isFriended){
            myFriendButton.getStyleClass().remove(FRIEND_BUTTON_CSS_HOVER);
            myFriendButton.getStyleClass().add(FRIEND_BUTTON_CSS_NORMAL);
            myFriendButton.setText(REMOVE_FRIEND_TEXT);
        } else {
            myFriendButton.getStyleClass().remove(FRIEND_BUTTON_CSS_NORMAL);
            myFriendButton.getStyleClass().add(FRIEND_BUTTON_CSS_HOVER);
            myFriendButton.setText(ADD_FRIEND_TEXT);
        }
    }

    @Override
    public String getName(){
        return myName;
    }

    @Override
    public StackPane getView() {
        return myPane;
    }
}
