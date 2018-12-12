package social;

import exceptions.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginScreen {
    public static final String LOGO_PATH = "duke_logo.png";
    public static final String MOTO = "Your Home for Games";
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");

    private GridPane myPane;
    private Scene myScene;
    private Stage myStage;

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
        Text register = new Text("Register");
        register.setOnMouseClicked(e -> {
            RegisterScreen myRegistration = new RegisterScreen();
            myRegistration.launchRegistration().show();
        });
        btn.setOnMouseClicked(e -> {
            try {
                loginUser(usernameField.getText(), passwordField.getText());
            } catch (Exception ex){
                ExtendedException exception = (ExtendedException) ex;
                new ErrorMessage(exception);
            }
        });
        myPane.add(usernameField, 0, 2, 4, 1);
        myPane.add(passwordField, 0, 3, 4, 1);
        myPane.add(btn, 0, 5, 4, 1);
        myPane.add(register, 0, 6);
    }

    private void loginUser(String myUsername, String myPassword) throws SQLException {
        try {
            checkForBlankFields(myUsername, myPassword);
            int id = DatabaseHelper.getIDByUsernameAndPassword(myUsername, myPassword);
            User user = DatabaseHelper.fetchUserFromDatabase(id);
            EventBus.getInstance().sendMessage(EngineEvent.CHANGE_USER, user);
            myStage.close();
        } catch (Exception ex){
            if (!ex.getClass().equals(LoginException.class)){
                throw new ServerException(myErrors.getString("ServerError"), myErrors.getString
                 ("ServerErrorWarning"));
            }
            throw ex;
        }
    }

    private void checkForBlankFields(String myUsername, String myPassword) {
        if (myUsername.isEmpty() || myPassword.isEmpty()){
            throw new LoginException(myErrors.getString("BlankField"), myErrors.getString(
                    "BlankFieldWarning"));
        }
    }

}
