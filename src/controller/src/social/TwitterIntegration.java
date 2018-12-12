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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import twitter4j.auth.RequestToken;
import util.files.ServerUploader;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

public class TwitterIntegration {
    public static final String PERSON_PATH = "/profile-images/person_logo.png";
    public static final String USERNAME_SIZING = "profile-username";

    private GridPane myPane;
    private Scene myScene;
    private Stage myStage;
    private User myUser;
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");

    public TwitterIntegration(User user) {
        myUser = user;
    }

    public Stage launchTwitterIntegration() {
        myStage = new Stage();
        initPane();
        initScene();
        initFields();
        myStage.setScene(myScene);
        myStage.setTitle("Twitter Integration");
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
        myScene = new Scene(myPane, 500.0D, 150.0D);
    }

    private void initFields() {
        RequestToken requestToken = myUser.getTwitterRequestToken();
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
            }
        } catch (Exception ex){
            ex.printStackTrace();
            new ErrorMessage(new UserException(myErrors.getString("InvalidURI"), myErrors.getString(
                    "InvalidURIWarning")));
        }
        Text instructions = new Text("Grant access in your browser. Then enter the PIN below:");
        TextField pin = new TextField();
        pin.setPromptText("Enter the PIN provided after granting access.");
        Button submit = new Button("Submit");
        submit.setPrefWidth(100.0D);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(submit);
        buttonBox.setAlignment(Pos.CENTER);
        setHoverListeners(submit);
        submit.setOnMouseClicked(e -> {
            try {
                myUser.verifyPin(pin.getText(), requestToken);
                if (myUser.isTwitterConfigured()) {
                    DatabaseHelper.uploadSerializedUserFile(myUser.getUsername(), myUser);
                    EventBus.getInstance().sendMessage(EngineEvent.INTEGRATED_TWITTER, myUser);
                }
                myStage.close();
            } catch (Exception ex){
                ex.printStackTrace();
                new ErrorMessage(new UserException(myErrors.getString("SerializationError"), myErrors.getString(
                        "SerializationErrorWarning")));
            }
        });
        myPane.add(instructions, 0, 0, 4, 1);
        myPane.add(pin, 0, 2, 4, 1);
        myPane.add(buttonBox, 0, 3, 4, 1);
    }

    private void setHoverListeners(Node node){
        node.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        node.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
    }

}
