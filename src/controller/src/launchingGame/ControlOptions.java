package launchingGame;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ControlOptions {
    public static final String CROSS_PATH = "/graphics/black-cross.png";
    public static final String MINIMIZE_PATH = "/graphics/black-minimize.png";
    public static final double BUTTON_WIDTH = 10;
    public static final double BUTTON_HEIGHT = 10;

    private HBox myBox;
    private Button myCloseButton;
    private Button myMinButton;
    private Stage myStage;

    public ControlOptions(Stage stage) {
        myStage = stage;

        initBox();
        initMinimize();
        initClose();
    }

    private void initBox() {
        myBox = new HBox();

        myBox.setAlignment(Pos.TOP_RIGHT);
    }

    private void initClose() {
        myCloseButton = new Button();

        javafx.scene.image.Image image = new Image(getClass().getResourceAsStream(CROSS_PATH));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(BUTTON_WIDTH);
        imageView.setFitHeight(BUTTON_HEIGHT);

        myCloseButton.setGraphic(imageView);
        myCloseButton.getStyleClass().add("closebutton");
        myCloseButton.setOnMouseClicked(event -> {
            Platform.exit();
        });

        myBox.getChildren().add(myCloseButton);
    }

    private void initMinimize() {
        myMinButton = new Button();

        javafx.scene.image.Image image = new Image(getClass().getResourceAsStream(MINIMIZE_PATH));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(BUTTON_WIDTH);
        imageView.setFitHeight(BUTTON_HEIGHT);

        myMinButton.setGraphic(imageView);
        myMinButton.getStyleClass().add("closebutton");
        myMinButton.setOnMouseClicked(event -> {
            myStage.setIconified(true);
        });

        myBox.getChildren().add(myMinButton);
    }

    public HBox getView() {
        return myBox;
    }
}
