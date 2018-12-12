package social;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProfileView {
    public static final String PERSON_PATH = "/profile-images/person_logo.png";
    public static final double ICON_WIDTH = 20;
    public static final double ICON_HEIGHT = 20;

    private HBox myBox;
    private Button myButton;
    private User myUser;
    private ImageView myImageView;

    public ProfileView(User user) {
        myUser = user;
        myBox = new HBox();
        myBox.setAlignment(Pos.CENTER_LEFT);
        initButton();
        EventBus.getInstance().register(EngineEvent.CHANGE_USER, this::reassignUser);
        EventBus.getInstance().register(EngineEvent.LOGGED_OUT, this::resetUser);
    }

    public HBox getView() {
        return myBox;
    }

    private void initButton() {
        myButton = new Button();
        myButton.getStyleClass().add("profile-button");
        setDefaultIcon();
        myBox.getChildren().add(myButton);
        myButton.setOnAction(event -> {
            if (myUser == null){
                LoginScreen myLogin = new LoginScreen();
                myLogin.launchLogin().show();
            } else {
                UserProfile userProfile = new UserProfile(myUser);
                userProfile.launchUserProfile().show();
            }
        });
    }

    private void changeIcon(ImageView imageView) {
        myImageView = imageView;
        myImageView.setFitWidth(ICON_WIDTH);
        myImageView.setFitHeight(ICON_HEIGHT);
        myButton.setGraphic(myImageView);
    }

    private void setDefaultIcon(){
        Image image = new Image(getClass().getResourceAsStream(PERSON_PATH));
        changeIcon(new ImageView(image));
    }

    private void reassignUser(Object... args) {
        myUser = (User) args[0];
        if (myUser != null && myUser.getAvatar() != null) {
            changeIcon(myUser.getAvatar());
        } else {
            setDefaultIcon();
        }
    }

    private void resetUser(Object... args){
        myUser = null;
        setDefaultIcon();
    }
}