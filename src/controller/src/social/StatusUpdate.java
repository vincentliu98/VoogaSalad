package social;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import exceptions.ErrorMessage;
import exceptions.ExtendedException;
import exceptions.UserException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.files.ServerUploader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

public class StatusUpdate {
    public static final String PERSON_PATH = "/profile-images/person_logo.png";
    public static final String USERNAME_SIZING = "profile-username";

    private GridPane myPane;
    private Scene myScene;
    private Stage myStage;
    private User myUser;
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");

    public StatusUpdate(User user) {
        myUser = user;
    }

    public Stage launchStatusUpdate() {
        myStage = new Stage();
        initPane();
        initScene();
        initFields();
        myStage.setScene(myScene);
        myStage.setTitle("Update Status");
        return myStage;
    }

    private void initPane() {
        myPane = new GridPane();
        myPane.setAlignment(Pos.TOP_CENTER);
        myPane.setVgap(15.0D);
        myPane.setPadding(new Insets(30.0D, 30.0D, 30.0D, 30.0D));

        for (int i = 0; i < 4; ++i) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25.0D);
            myPane.getColumnConstraints().add(col);
        }
        myPane.setGridLinesVisible(false);
    }

    private void initScene() {
        myScene = new Scene(myPane, 300.0D, 150.0D);
    }

    private void initFields() {
        Text instructions = new Text("Write your status below.");
        TextField status = new TextField();
        status.setPromptText("Type your status here.");
        Button post = new Button("Post");
        post.setPrefWidth(100.0D);
        HBox motoBox = new HBox();
        motoBox.getChildren().add(post);
        motoBox.setAlignment(Pos.CENTER);
        setHoverListeners(post);
        post.setOnMouseClicked(e -> {
            try {
                myUser.updateStatus(status.getText());
                DatabaseHelper.uploadSerializedUserFile(myUser.getUsername(), myUser);
                EventBus.getInstance().sendMessage(EngineEvent.UPDATED_STATUS, myUser);
                myStage.close();
            } catch (Exception ex){
                ex.printStackTrace();
                new ErrorMessage(new UserException(myErrors.getString("SerializationError"), myErrors.getString(
                        "SerializationErrorWarning")));
            }
        });
        myPane.add(instructions, 0, 0, 4, 1);
        myPane.add(status, 0, 2, 4, 1);
        myPane.add(motoBox, 0, 3, 4, 1);
    }

    private void setHoverListeners(Node node){
        node.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        node.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
    }

}
