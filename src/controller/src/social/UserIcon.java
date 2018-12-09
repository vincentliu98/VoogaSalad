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
    public static final String FOLLOW_TEXT = "Follow";
    public static final String UNFOLLOW_TEXT = "Unfollow";
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
    private boolean isFollowing;
    private Button myFollowButton;
    private HBox myButtonHolder;
    private String myName;


    public UserIcon(String name, ImageView avatar, boolean follows){
        myName = name;
        myBackground = avatar;
        isFollowing = follows;
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
        isFollowing = false;
        myFollowButton = new Button();
        myFollowButton.setTextFill(Color.WHITE);
        myFollowButton.setPrefWidth(FRIEND_BUTTON_WIDTH);
        myFollowButton.setPrefHeight(FRIEND_BUTTON_HEIGHT);
        changeButtonDisplay();
        myFollowButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isFollowing = !isFollowing;
                changeButtonDisplay();
            }
        });
        myButtonHolder = new HBox();
        myButtonHolder.getChildren().add(myFollowButton);
        myButtonHolder.setAlignment(Pos.BOTTOM_RIGHT);
        myButtonHolder.getStyleClass().add(BUTTON_HOLDER_CSS);
        myPane.getChildren().add(myButtonHolder);
    }

    private void changeButtonDisplay(){
        if (isFollowing){
            myFollowButton.getStyleClass().remove(FRIEND_BUTTON_CSS_HOVER);
            myFollowButton.getStyleClass().add(FRIEND_BUTTON_CSS_NORMAL);
            myFollowButton.setText(UNFOLLOW_TEXT);
        } else {
            myFollowButton.getStyleClass().remove(FRIEND_BUTTON_CSS_NORMAL);
            myFollowButton.getStyleClass().add(FRIEND_BUTTON_CSS_HOVER);
            myFollowButton.setText(FOLLOW_TEXT);
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
