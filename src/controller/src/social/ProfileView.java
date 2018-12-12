package social;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProfileView implements Subscriber {
    public static final String PERSON_PATH = "/profile-images/person_logo.png";
    public static final double ICON_WIDTH = 20;
    public static final double ICON_HEIGHT = 20;

    private HBox myBox;
    private Button myButton;
    private User myUser;

    public ProfileView(User user) {
        myUser = user;
        myBox = new HBox();
        myBox.setAlignment(Pos.CENTER_LEFT);
        initButton();
        EventBus.getInstance().register(EngineEvent.CHANGE_USER, this);
    }

    public HBox getView() {
        return myBox;
    }

    private void initButton() {
        myButton = new Button();
        myButton.getStyleClass().add("profile-button");
        setDefaultIcon();
        myButton.setOnAction(event -> {
            if (myUser == null){
                LoginScreen myLogin = new LoginScreen();
                myLogin.launchLogin().show();
            } else {
                UserProfile userProfile = new UserProfile(myUser);
                userProfile.launchUserProfile().show();
            }
        });
        myBox.getChildren().add(myButton);
    }

    private void changeIcon(ImageView imageView) {
        imageView.setFitWidth(ICON_WIDTH);
        imageView.setFitHeight(ICON_HEIGHT);
        myButton.setGraphic(imageView);
    }

    private void setDefaultIcon(){
        Image image = new Image(getClass().getResourceAsStream(PERSON_PATH));
        changeIcon(new ImageView(image));
    }

    @Override
    public void update(EngineEvent engineEvent, Object... args) {
        if (engineEvent.equals(EngineEvent.CHANGE_USER) && args[0].getClass().equals(User.class)) {
            myUser = (User) args[0];
            if (myUser.getAvatar() != null) {
                changeIcon(myUser.getAvatar());
            } else {
                setDefaultIcon();
            }
        }
    }
}