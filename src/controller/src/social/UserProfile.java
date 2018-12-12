package social;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import exceptions.ErrorMessage;
import exceptions.UserException;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.data.DatabaseUploader;
import util.files.ServerUploader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private Button myTwitterButton;
    private FileChooser myFileChooser;

    public UserProfile(User user) {
        myUser = user;
        myFileChooser = new FileChooser();
        myFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files",
                "*.png", "*.jpg", "*.jpeg"));
        EventBus.getInstance().register(EngineEvent.UPDATED_STATUS, this::updateStatus);
        EventBus.getInstance().register(EngineEvent.INTEGRATED_TWITTER, this::updateTwitterButton);
    }

    public Stage launchUserProfile() {
        myStage = new Stage();
        initPane();
        initScene();
        initAvatar();
        initStatus();
        initFields();
        myStage.setScene(myScene);
        myStage.setTitle("User Profile");
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
            avatar = new ImageView(myUser.getAvatar().getImage());
        }
        avatar.setFitWidth(100.0D);
        avatar.setPreserveRatio(true);
        HBox imageBox = new HBox();
        imageBox.getChildren().add(avatar);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setOnMouseClicked(e -> {
            File file = myFileChooser.showOpenDialog(myStage);
            if (file != null){
                String[] fullName = file.getName().split("\\.");
                String extension = fullName[fullName.length - 1];
                File localAvatarFile = new File("src/controller/resources/profile-images/" + myUser.getUsername() + "-Avatar." + extension);
                try {
                    if (!localAvatarFile.exists()) {
                        localAvatarFile.createNewFile();
                    }
                    file.renameTo(localAvatarFile);
                    DatabaseHelper.updateRemoteAvatar(localAvatarFile, myUser.getID());
                    myUser.changeAvatar(localAvatarFile.getName());
                    avatar.setImage(myUser.getAvatar().getImage());
                    EventBus.getInstance().sendMessage(EngineEvent.CHANGE_USER, myUser);
                } catch (IOException ex){
                    ex.printStackTrace();
                    new ErrorMessage(new UserException(myErrors.getString("IOError"), myErrors.getString(
                            "IOErrorWarning")));
                }
            }
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
            StatusUpdate statusUpdate = new StatusUpdate(myUser);
            Stage suStage = statusUpdate.launchStatusUpdate();
            suStage.show();
        });
        setHoverListeners(motoBox);
        myPane.add(motoBox, 0, 3, 4, 1);
    }

    private void updateStatus(Object... args){
        User user = (User) args[0];
        if (user.equals(myUser)) myStatus.setText("Status: " + myUser.getStatus());
    }

    private void updateTwitterButton(Object... args){
        User user = (User) args[0];
        if (user.equals(myUser)) {
            configureTwitterButton();
        }
    }

    private void configureTwitterButton(){
        if (myUser.isTwitterConfigured()){
            myTwitterButton.setText("Remove Twitter account");
            myTwitterButton.setStyle("-fx-background-color: #adbbd1");
            myTwitterButton.setOnMouseClicked(e -> {
                myUser.removeTwitter();
                try {
                    DatabaseHelper.uploadSerializedUserFile(myUser.getUsername(), myUser);
                    EventBus.getInstance().sendMessage(EngineEvent.INTEGRATED_TWITTER, myUser);
                } catch (Exception ex){
                    ex.printStackTrace();
                    new ErrorMessage(new UserException(myErrors.getString("IOError"), myErrors.getString(
                            "IOErrorWarning")));
                }
            });
        } else {
            myTwitterButton.setText("Add Twitter account");
            myTwitterButton.setStyle("-fx-background-color: #38A1F3");
            myTwitterButton.setOnMouseClicked(e -> new TwitterIntegration(myUser).launchTwitterIntegration().show());
        }
    }

    private void initFields() {
        Text username = new Text(myUser.getUsername());
        username.setFont(Font.font ("Arial", 30));
        HBox nameBox = new HBox();
        nameBox.getChildren().add(username);
        nameBox.setAlignment(Pos.CENTER);
        myTwitterButton = new Button();
        myTwitterButton.setPrefWidth(260.0D);
        configureTwitterButton();
        setHoverListeners(myTwitterButton);
        Text logout = new Text("Log Out");
        logout.setOnMouseClicked(e -> logout());
        setHoverListeners(logout);
        myPane.add(nameBox, 0, 0, 4, 1);
        myPane.add(myTwitterButton, 0, 5, 4, 1);
        myPane.add(logout, 0, 6);
    }

    private void logout(){
        EventBus.getInstance().sendMessage(EngineEvent.LOGGED_OUT); // sets to null user
        myStage.close();
    }

    private void setHoverListeners(Node node){
        node.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        node.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
    }

}
