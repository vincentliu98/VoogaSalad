package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;

public class SoundView {
    public final int HEIGHT = 200;
    public final int WIDTH = 400;
    public static final Double ICON_WIDTH = 50.0;
    public static final Double ICON_HEIGHT = 50.0;
    Image startImg = new Image(
            this.getClass().getClassLoader().getResourceAsStream("startAudio.png"),
            ICON_WIDTH, ICON_HEIGHT, true, true
    );
    Image stopImg = new Image(
                this.getClass().getClassLoader().getResourceAsStream("stopAudio.png"),
            ICON_WIDTH, ICON_HEIGHT, true, true
    );

    private Label label;
    private Stage stage;
    VBox root;
    HBox audioRoot;

    public SoundView() {
        root = new VBox();
        audioRoot = new HBox();
        audioRoot.setSpacing(10);
        root.setSpacing(10);
        root.setPadding(new Insets(50, 0, 0, 135));

        this.label = new Label();
        stage = new Stage();

        Button button = new Button("Select audio file");
        button.setStyle("-fx-font-size: 15px;"
                + "-fx-background-color:  #80cbc4;"
                + "-fx-text-fill: white;"
                + "-fx-cursor: hand;");
        button.setOnMouseClicked(this::musicChooser);

        root.getChildren().addAll(button, audioRoot);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Select Background Music");
        stage.setScene(scene);

    }

    public void show() {
        stage.show();
    }

    private void musicChooser(MouseEvent mouseEvent) {
        ExtensionFilter filter = new ExtensionFilter(
                "Audio files", "*.mp3", "*.m4a");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Select Background Music");
        File file = fileChooser.showOpenDialog(new Stage());
        try{
            this.label.setText(file.getName());
            var image = new ImageView(startImg);
            image.setStyle("-fx-cursor: hand;");
            audioRoot.getChildren().addAll(image, label);
            image.setOnMouseClicked(this::playMusic);
        }
        catch (Exception e){
        }
    }

    private void playMusic(MouseEvent mouseEvent) {
        var image = new ImageView(stopImg);
        image.setStyle("-fx-cursor: hand;");
        audioRoot.getChildren().clear();
        audioRoot.getChildren().addAll(image, label);
        image.setOnMouseClicked(this::stopMusic);
    }

    private void stopMusic(MouseEvent mouseEvent) {
        var image = new ImageView(startImg);
        image.setStyle("-fx-cursor: hand;");
        audioRoot.getChildren().clear();
        audioRoot.getChildren().addAll(image, label);
        image.setOnMouseClicked(this::playMusic);
    }

}
