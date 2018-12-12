package social;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.DomDriver;
import exceptions.ExtendedException;
import exceptions.LoginException;
import exceptions.RegistrationException;
import exceptions.ServerException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.data.DatabaseDownloader;
import util.data.DatabaseUploader;
import util.files.ServerDownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginScreen {
    public static final String LOGO_PATH = "duke_logo.png";
    public static final String MOTO = "Your Home for Games";

    private GridPane myPane;
    private Scene myScene;
    private Stage myStage;
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");

    public LoginScreen() {
    }

    public Stage launchLogin() {
        myStage = new Stage();

        initPane();
        initScene();
        initLogo();
        initMoto();
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
        myScene = new Scene(myPane, 400.0D, 500.0D);
    }

    private void initLogo() {
        Image logoStream = new Image(LOGO_PATH);
        ImageView logo = new ImageView(logoStream);
        logo.setFitWidth(100.0D);
        logo.setPreserveRatio(true);

        HBox imageBox = new HBox();
        imageBox.getChildren().add(logo);
        imageBox.setAlignment(Pos.CENTER);

        myPane.add(imageBox, 1, 0, 2, 1);
    }

    private void initMoto() {
        Text motoText = new Text(MOTO);
        HBox motoBox = new HBox();
        motoBox.getChildren().add(motoText);
        motoBox.setAlignment(Pos.CENTER);

        myPane.add(motoBox, 0, 1, 4, 1);
    }

    private void initFields() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("password");
        Button btn = new Button("LOGIN");
        btn.setPrefWidth(260.0D);
        //CheckBox cBox = new CheckBox("Remember me"); TODO: Do we need this?
        Text register = new Text("Register");
        register.setOnMouseClicked(e -> {
            RegisterScreen myRegistration = new RegisterScreen();
            myRegistration.launchRegistration().show();
        });
        //Text forgotPassword = new Text("Forgot your password?");

        btn.setOnMouseClicked(e -> {
            try {
                loginUser(usernameField.getText(), passwordField.getText());
            } catch (Exception ex){
                ex.printStackTrace();
                ExtendedException exception = (ExtendedException) ex;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(exception.getMessage());
                alert.setContentText(exception.getWarning());
                alert.showAndWait();
                return;
            }
        });

        myPane.add(usernameField, 0, 2, 4, 1);
        myPane.add(passwordField, 0, 3, 4, 1);
        //myPane.add(cBox, 0, 4, 2, 1);
        myPane.add(btn, 0, 5, 4, 1);
        myPane.add(register, 0, 6);
        // grid.add(forgotPassword, 2, 6);
    }

    private void loginUser(String myUsername, String myPassword) throws SQLException, FileNotFoundException {
        try {
            checkForBlankFields(myUsername, myPassword);
            int id = retrieveUserID(myUsername, myPassword);
            String remoteProfilePath = getProfilePath(id);
            String remoteAvatarPath = getAvatarPath(id);
            String profileFilename = downloadRemoteXML(remoteProfilePath); // filename of local XML (to be deleted)
            String avatarFilename = downloadRemoteAvatar(remoteAvatarPath); // filename of local image
            User user = deserializeUser(profileFilename);
            System.out.println("avatarFilename is " + avatarFilename);
            user.changeAvatar(avatarFilename);
            EventBus.getInstance().sendMessage(EngineEvent.CHANGE_USER, user);
            myStage.close();
            //User myUser = new User(10, "bloop");// TODO: Remove later (just a placeholder)
            //myUser.changeAvatar("ocean.jpeg");
            //EventBus.getInstance().sendMessage(EngineEvent.CHANGE_USER, myUser);
            //myStage.close();
        } catch (SQLException | LoginException | FileNotFoundException ex){
            if (!ex.getClass().equals(LoginException.class)){
                throw new ServerException(myErrors.getString("ServerError"), myErrors.getString("ServerErrorWarning"));
            }
            throw ex; // rethrowing the LoginException
        }
    }

    private String getProfilePath(int id) throws SQLException {
        DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        ResultSet result = databaseDownloader.queryServer(String.format("SELECT profilePath FROM" +
                " userReferences WHERE id='%d'", id));
        result.last();
        return result.getString("profilePath");
    }

    private String getAvatarPath(int id) throws SQLException {
        DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        ResultSet result = databaseDownloader.queryServer(String.format("SELECT avatarPath FROM" +
                " userReferences WHERE id='%d'", id));
        result.last();
        return result.getString("avatarPath");
    }

    private String downloadRemoteXML(String profilePath) throws SQLException {
        ServerDownloader downloader = new ServerDownloader();
        downloader.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
        File directory = new File("src/database/resources/");
        downloader.downloadFile(profilePath,directory.getAbsolutePath());
        String[] filePathArray = profilePath.split("/");
        return filePathArray[filePathArray.length - 1];
    }

    private String downloadRemoteAvatar(String avatarPath) throws SQLException {
        ServerDownloader downloader = new ServerDownloader();
        downloader.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
        File directory = new File("src/controller/resources/profile-images/");
        System.out.println("avatar path is " + avatarPath);
        downloader.downloadFile(avatarPath, directory.getAbsolutePath());
        String[] filePathArray = avatarPath.split("/");
        return filePathArray[filePathArray.length - 1];
    }

    private User deserializeUser(String fileName) throws FileNotFoundException {
        XStream serializer = new XStream(new DomDriver());
        File file = new File("src/database/resources/" + fileName);
        Scanner scanner = new Scanner(file, "UTF-8" );
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        text = text.trim().replaceFirst("^([\\W]+)<","<");
        file.delete();
        return (User) serializer.fromXML(text);
    }

    private int retrieveUserID(String myUsername, String myPassword) throws SQLException {
        DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        ResultSet result = databaseDownloader.queryServer(String.format("SELECT id FROM logins WHERE " +
                "username='%s' AND password='%s'", myUsername, myPassword));
        if (!result.next()){
            throw new LoginException(myErrors.getString("NonexistentAccount"), myErrors.getString(
                    "NonexistentAccountWarning"));
        }
        result.last();
        return result.getInt("id");
    }

    private void checkForBlankFields(String myUsername, String myPassword) {
        if (myUsername.isEmpty() || myPassword.isEmpty()){
            throw new LoginException(myErrors.getString("BlankField"), myErrors.getString(
                    "BlankFieldWarning"));
        }
    }

}
