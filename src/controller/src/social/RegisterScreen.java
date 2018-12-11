package social;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterScreen {
    public static final String MOTTO = "Enter a username and password below.";

    private GridPane myPane;
    private Scene myScene;
    private Stage myStage;

    public RegisterScreen() { }

    public Stage launchRegistration(){
        myStage = new Stage();

        initPane();
        initScene();
        initMotto();
        initFields();

        myStage.setScene(myScene);
        myStage.setTitle("Registration");
        return myStage;
    }

    private void initPane(){
        myPane = new GridPane();
        myPane.setAlignment(Pos.TOP_CENTER);
        myPane.setVgap(15.0D);
        myPane.setPadding(new Insets(40.0D, 70.0D, 40.0D, 70.0D));

        for(int i = 0; i < 4; ++i) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25.0D);
            myPane.getColumnConstraints().add(col);
        }

        myPane.setGridLinesVisible(false);
    }

    private void initScene(){
        myScene = new Scene(myPane, 400.0D, 500.0D);
    }

    private void initMotto(){
        Text mottoText = new Text(MOTTO);
        HBox mottoBox = new HBox();
        mottoBox.getChildren().add(mottoText);
        mottoBox.setAlignment(Pos.CENTER);

        myPane.add(mottoBox, 0, 1, 4, 1);
    }

    private void initFields(){
        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("password");
        Button btn = new Button("CREATE ACCOUNT");
        btn.setPrefWidth(260.0D);

        btn.setOnMouseClicked(e -> {
            // TODO: Check for username not taken, add user
            DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                    "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
            ResultSet result = databaseDownloader.queryServer("SELECT username, id FROM logins");
            try {
                while (result.next()){
                    String username = result.getString("username");
                    if (username.equals(usernameField.getText())){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Username already taken.");
                        alert.setContentText("Choose a new username.");
                        alert.showAndWait();
                        return;
                    }
                }
                result.last();
                int id = result.getInt("id") + 1; // TODO: Add case for no users in database
                DatabaseUploader databaseUploader = new DatabaseUploader("client", "store",
                        "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
                databaseUploader.upload(String.format("INSERT INTO logins (username, id, password) VALUES ('%s','%d'," +
                                "'%s')", usernameField.getText(), id, passwordField.getText()));
                User user = new User(id, usernameField.getText());
                XStream serializer = new XStream(new DomDriver());
                File userFile=new File("src/database/resources/" + usernameField.getText() + ".xml");
                userFile.createNewFile();
                FileWriter fileWriter = new FileWriter(userFile);
                fileWriter.write(serializer.toXML(user));
                fileWriter.close();
                ServerUploader upload = new ServerUploader();
                upload.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
                upload.uploadFile(userFile.getAbsolutePath(), "/users/profiles");
                databaseUploader.upload(String.format("INSERT INTO userReferences (id, profilePath) VALUES ('%d', " +
                        "'%s')", id, "/home/vcm/public_html/users/profiles/" + usernameField.getText() + ".xml"));
                userFile.delete();
            } catch (Exception ex){
                ex.printStackTrace();
            }
            //resetDatabases();
            myStage.close();
        });
        myPane.add(usernameField, 0, 2, 4, 1);
        myPane.add(passwordField, 0, 3, 4, 1);
        myPane.add(btn, 0, 5, 4, 1);
    }

    /**
     * For debugging purposes only
     */
    private void resetDatabases(){
        DatabaseUploader databaseUploader = new DatabaseUploader("client", "store",
        "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        databaseUploader.upload("DELETE FROM logins WHERE id!='1'");
        databaseUploader.upload("DELETE FROM userReferences WHERE id!='1'");
    }
}
