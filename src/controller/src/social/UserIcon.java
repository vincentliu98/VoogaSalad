package social;

import exceptions.ErrorMessage;
import exceptions.ExtendedException;
import exceptions.UserException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import java.util.ResourceBundle;

public class UserIcon extends Icon {

    public static final String UNFOLLOW_TEXT = "Following";
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");
    private boolean isFollowing;

    public UserIcon(String gameName, String description, String reference, String color, String imagePath,
                    String tags, User user, String buttonText, String imgFolderPath) {
        super(gameName, description, reference, color, imagePath, tags, user, "Follow", "src/controller/resources" +
                "/profile-images/");
        myButtonHolder.setAlignment(Pos.BOTTOM_RIGHT);
        changeButtonDisplay();
    }

    @Override
    public void initButtonHandlers() {
        myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (myUser == null || myUser.getUsername().equals(myName)) { // TODO: Turn into an error
                    new ErrorMessage(new ExtendedException(myErrors.getString("NoUser"), myErrors.getString(
                            "NoUserWarning")));
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
        try {
            int id = DatabaseHelper.getIDByUsername(myName);
            User u = DatabaseHelper.fetchUserFromDatabase(id);
            if (isFollowing) {
                myButton.getStyleClass().remove(BUTTON_CSS_HOVER);
                myButton.getStyleClass().add(BUTTON_CSS_NORMAL);
                myButton.setText(UNFOLLOW_TEXT);
                myUser.follow(myName);
                u.addFollower(myUser.getUsername());
            } else {
                myButton.getStyleClass().remove(BUTTON_CSS_NORMAL);
                myButton.getStyleClass().add(BUTTON_CSS_HOVER);
                myButton.setText(BUTTON_TEXT);
                myUser.unfollow(myName);
                u.removeFollower(myUser.getUsername());
            }
            DatabaseHelper.uploadSerializedUserFile(u.getUsername(), u);
        } catch (Exception e){
            e.printStackTrace();
            new ErrorMessage(new UserException(myErrors.getString("SerializationError"), myErrors.getString(
                    "SerializationErrorWarning")));
        }
    }
}
