package launchingGame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen {
    public static final String LOGO_PATH = "duke_logo.png";
    public static final String MOTO = "Your Home for Games";


    private GridPane myPane;
    private Scene myScene;

    public LoginScreen() {
    }

    public Stage launchLogin(){
        Stage myStage = new Stage();

        initPane();
        initScene();
        initLogo();
        initMoto();
        initFields();

        myStage.setScene(myScene);
        myStage.setTitle("Login");
        return myStage;
    }

    public void initPane(){
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

    public void initScene(){
        myScene = new Scene(myPane, 400.0D, 500.0D);
    }

    public void initLogo(){
        Image logoStream = new Image(LOGO_PATH);
        ImageView logo = new ImageView(logoStream);
        logo.setFitWidth(100.0D);
        logo.setPreserveRatio(true);

        HBox imageBox = new HBox();
        imageBox.getChildren().add(logo);
        imageBox.setAlignment(Pos.CENTER);

        myPane.add(imageBox, 1, 0, 2, 1);
    }

    public void initMoto(){
        Text motoText = new Text(MOTO);
        HBox motoBox = new HBox();
        motoBox.getChildren().add(motoText);
        motoBox.setAlignment(Pos.CENTER);

        myPane.add(motoBox, 0, 1, 4, 1);
    }

    public void initFields(){
        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("password");
        Button btn = new Button("LOGIN");
        btn.setPrefWidth(260.0D);
        CheckBox cBox = new CheckBox("Remember me");
        Text register = new Text("Register");
        //Text forgotPassword = new Text("Forgot your password?");

        myPane.add(usernameField, 0, 2, 4, 1);
        myPane.add(passwordField, 0, 3, 4, 1);
        myPane.add(cBox, 0, 4, 2, 1);
        myPane.add(btn, 0, 5, 4, 1);
        myPane.add(register, 0, 6);
        // grid.add(forgotPassword, 2, 6);
    }
}
