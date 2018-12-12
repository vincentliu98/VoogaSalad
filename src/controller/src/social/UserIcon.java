package social;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class UserIcon extends Icon {

    public static final String UNFOLLOW_TEXT = "Following";
    private boolean isFollowing;

    public UserIcon(String gameName, String description, String reference, String color, String imagePath,
                    String tags, User user, String buttonText, String imgFolderPath) {
        super(gameName, description, reference, color, imagePath, tags, user, "Follow", "/profile-images/");
        myButtonHolder.setAlignment(Pos.BOTTOM_RIGHT);
        changeButtonDisplay();
    }

    @Override
    public void initButtonHandlers() {
        myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (myUser.getUsername().equals(myName)) { // TODO: Turn into an error
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Cannot follow others without a profile.");
                    alert.setContentText("Log in or create a new profile.");
                    alert.showAndWait();
                } else {
                    isFollowing = !isFollowing;
                    changeButtonDisplay();
                }
            }
        });
    }

    protected void initPane() {
        myPane = new StackPane();

        myPane.setOnMouseEntered(event -> { // always want username
            myPane.getChildren().add(myDescriptionHolder);
            myPane.getChildren().add(myButtonHolder);
        });

        myPane.setOnMouseExited(event -> {
            myPane.getChildren().remove(myDescriptionHolder);
            myPane.getChildren().remove(myButtonHolder);
        });

    }

    private void changeButtonDisplay() {
        if (isFollowing) {
            myButton.getStyleClass().remove(BUTTON_CSS_HOVER);
            myButton.getStyleClass().add(BUTTON_CSS_NORMAL);
            myButton.setText(UNFOLLOW_TEXT);
            System.out.println("myUser name is " + myUser.getUsername());
            System.out.println("followed is " + myName);
            myUser.addFollower(myName);
        } else {
            myButton.getStyleClass().remove(BUTTON_CSS_NORMAL);
            myButton.getStyleClass().add(BUTTON_CSS_HOVER);
            myButton.setText(BUTTON_TEXT);
            myUser.removeFollower(myName);
        }
    }
}
