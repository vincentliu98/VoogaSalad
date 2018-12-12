package social;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ResourceBundle;

public class UserProfile {
    public static final String PERSON_PATH = "/profile-images/person_logo.png";
    public static final String USERNAME_SIZING = "profile-username";

    private GridPane myPane;
    private Scene myScene;
    private Stage myStage;
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");
    private User myUser;
    private Text myStatus;

    public UserProfile(User user) {
        myUser = user;
    }

    public Stage launchUserProfile() {
        myStage = new Stage();
        initPane();
        initScene();
        initAvatar();
        initStatus();
        initFields();
        myStage.setScene(myScene);
        myStage.setTitle("Login");
        return myStage;
    }

    private void initPane() {
        myPane = new GridPane();
        myPane.setAlignment(Pos.TOP_CENTER);
        myPane.setVgap(15.0D);
        myPane.setPadding(new Insets(40.0D, 70.0D, 40.0D, 70.0D));

        for (int i = 0; i < 4; ++i) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25.0D);
            myPane.getColumnConstraints().add(col);
        }

        myPane.setGridLinesVisible(false);
    }

    private void initScene() {
        myScene = new Scene(myPane, 400.0D, 350.0D);
    }

    private void initAvatar() {
        ImageView avatar;
        if (myUser.getAvatar() == null){
            avatar = new ImageView(new Image(getClass().getResourceAsStream(PERSON_PATH)));
        } else {
            avatar = myUser.getAvatar();
        }
        avatar.setFitWidth(100.0D);
        avatar.setPreserveRatio(true);
        HBox imageBox = new HBox();
        imageBox.getChildren().add(avatar);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setOnMouseClicked(e -> {
            // set listener here
        });
        setHoverListeners(imageBox);
        myPane.add(imageBox, 1, 1, 2, 1);
    }

    private void initStatus() {
        myStatus = new Text("Status: " + myUser.getStatus());
        HBox motoBox = new HBox();
        motoBox.getChildren().add(myStatus);
        motoBox.setAlignment(Pos.CENTER);
        motoBox.setOnMouseClicked(e -> {
            // set listener here
        });
        setHoverListeners(motoBox);
        myPane.add(motoBox, 0, 3, 4, 1);
    }

    private void initFields() {
        Text username = new Text(myUser.getUsername());
        username.setFont(Font.font ("Arial", 30));
        HBox nameBox = new HBox();
        nameBox.getChildren().add(username);
        nameBox.setAlignment(Pos.CENTER);
        Button btn = new Button("Add Twitter");
        btn.setPrefWidth(260.0D);
        btn.setStyle("-fx-background-color: #38A1F3");
        setHoverListeners(btn);
        //CheckBox cBox = new CheckBox("Remember me"); TODO: Do we need this?
        Text logout = new Text("Log Out");
        logout.setOnMouseClicked(e -> {
            EventBus.getInstance().sendMessage(EngineEvent.CHANGE_USER); // sets to null user
        });
        setHoverListeners(logout);
        //Text forgotPassword = new Text("Forgot your password?");

        btn.setOnMouseClicked(e -> myUser.configureTwitter());

//        myPane.add(updateStatusField, 0, 2, 4, 1);
//        myPane.add(passwordField, 0, 3, 4, 1);
        //myPane.add(cBox, 0, 4, 2, 1);
        myPane.add(nameBox, 0, 0, 4, 1);
        myPane.add(btn, 0, 5, 4, 1);
        myPane.add(logout, 0, 6);
        // grid.add(forgotPassword, 2, 6);
    }

    private void setHoverListeners(Node node){
        node.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        node.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
    }

}
