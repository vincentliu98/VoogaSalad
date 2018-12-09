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

    public ProfileView(){
        myBox = new HBox();
        myBox.setAlignment(Pos.CENTER_LEFT);
        initButton();
    }

    public HBox getView(){
        return myBox;
    }

    private void initButton(){
        myButton = new Button();
        myButton.getStyleClass().add("profile-button");

        Image image = new Image(getClass().getResourceAsStream(PERSON_PATH));
        changeIcon(new ImageView(image));

        myButton.setOnAction(event -> {
            LoginScreen myLogin = new LoginScreen();
            myLogin.launchLogin().show();
        });

        myBox.getChildren().add(myButton);
    }

    private void changeIcon(ImageView imageView){
        imageView.setFitWidth(ICON_WIDTH);
        imageView.setFitHeight(ICON_HEIGHT);
        myButton.setGraphic(imageView);
    }

    @Override
    public void update(EngineEvent engineEvent, Object... args) {
        if (engineEvent.equals(EngineEvent.CHANGE_USER) && args[0].getClass().equals(User.class)){
            User user = (User) args[0];
            changeIcon(user.getAvatar());
        }
    }
}