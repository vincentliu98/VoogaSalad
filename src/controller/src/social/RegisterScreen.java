package social;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import exceptions.ErrorMessage;
import exceptions.ExtendedException;
import exceptions.RegistrationException;
import exceptions.ServerException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.data.DatabaseDownloader;
import util.data.DatabaseUploader;
import util.files.ServerUploader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterScreen {
    public static final String MOTTO = "Enter a username and password below.";

    private GridPane myPane;
    private Scene myScene;
    private Stage myStage;
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");

    public RegisterScreen() {
    }

    public Stage launchRegistration() {
        myStage = new Stage();

        initPane();
        initScene();
        initMotto();
        initFields();

        myStage.setScene(myScene);
        myStage.setTitle("Registration");
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
        myScene = new Scene(myPane, 400.0D, 500.0D);
    }

    private void initMotto() {
        Text mottoText = new Text(MOTTO);
        HBox mottoBox = new HBox();
        mottoBox.getChildren().add(mottoText);
        mottoBox.setAlignment(Pos.CENTER);

        myPane.add(mottoBox, 0, 1, 4, 1);
    }

    private void initFields() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("password");
        Button btn = new Button("CREATE ACCOUNT");
        btn.setPrefWidth(260.0D);
        btn.setOnMouseClicked(e -> {
            try {
                registerUser(usernameField.getText(), passwordField.getText());
            } catch (Exception ex){
                ExtendedException exception = (ExtendedException) ex;
                new ErrorMessage(exception);
            }
        });
        myPane.add(usernameField, 0, 2, 4, 1);
        myPane.add(passwordField, 0, 3, 4, 1);
        myPane.add(btn, 0, 5, 4, 1);
    }

    private void registerUser(String myUsername, String myPassword) throws IOException, SQLException {
        try {
            checkForBlankFields(myUsername, myPassword);
            DatabaseHelper.verifyUniqueUsername(myUsername);
            int id = DatabaseHelper.getNextUserID();
            User user = new User(id, myUsername);
            DatabaseHelper.updateLoginsTable(myUsername, myPassword, id);
            DatabaseHelper.uploadSerializedUserFile(myUsername, user);
            DatabaseHelper.updateRemoteProfileReferences(myUsername, id);
        } catch (IOException | SQLException | RegistrationException ex){
            if (!ex.getClass().equals(RegistrationException.class)){
                throw new ServerException(myErrors.getString("ServerError"), myErrors.getString("ServerErrorWarning"));
            }
            throw ex; // rethrowing the RegistrationException
        }
//       resetDatabases();
        myStage.close();
    }

    private void checkForBlankFields(String myUsername, String myPassword) {
        if (myUsername.isEmpty() || myPassword.isEmpty()){
            throw new RegistrationException(myErrors.getString("BlankField"), myErrors.getString(
                    "BlankFieldWarning"));
        }
    }

    /**
     * For debugging purposes only
     */
    private void resetDatabases(){
        DatabaseUploader databaseUploader = new DatabaseUploader("client", "store",
        "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        databaseUploader.upload("DELETE FROM logins WHERE id!='0'");
        databaseUploader.upload("DELETE FROM userReferences WHERE id!='0'");
    }
}
