package launchingGame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProfileView {
    public static final String PERSON_PATH = "/graphics/person_logo.png";
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

        Image image = new Image(getClass().getResourceAsStream(PERSON_PATH));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(ICON_WIDTH);
        imageView.setFitHeight(ICON_HEIGHT);

        myButton = new Button();
        myButton.setGraphic(imageView);

        myButton.getStyleClass().add("profile-button");

        myButton.setOnAction(event -> {
            LoginScreen myLogin = new LoginScreen();
            myLogin.launchLogin().show();
        });

        myBox.getChildren().add(myButton);
    }
}
