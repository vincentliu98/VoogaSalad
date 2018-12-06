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
import java.nio.file.Path;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class SoundView {
    public static final int HEIGHT = 200;
    public static final int WIDTH = 280;
    public static final Double ICON_WIDTH = 50.0;
    public static final Double ICON_HEIGHT = 50.0;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private Label label;
    private Stage stage;
    private VBox root;
    private HBox audioRoot;
    private String path;

    Image startImg = new Image(
            this.getClass().getClassLoader().getResourceAsStream("startAudio.png"),
            ICON_WIDTH, ICON_HEIGHT, true, true
    );
    Image stopImg = new Image(
                this.getClass().getClassLoader().getResourceAsStream("stopAudio.png"),
            ICON_WIDTH, ICON_HEIGHT, true, true
    );


    public SoundView() {
        root = new VBox();
        root.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #868c87;");
        audioRoot = new HBox();
        audioRoot.setSpacing(10);
        root.setSpacing(10);
        root.setPadding(new Insets(50, 0, 0, 75));

        this.label = new Label();
        label.getStyleClass().add("labelAudio");
        label.setStyle("-fx-font-size: 14px;"
                + "-fx-text-fill: dimgrey;"
                + "-fx-padding: 10");
        stage = new Stage();

        Button button = new Button("Select audio file");
        button.setStyle("-fx-font-size: 15px;"
                + "-fx-background-color: #343a40;"
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
        file = fileChooser.showOpenDialog(new Stage());
        try{
            this.label.setText(file.getName());
            var image = new ImageView(startImg);
            image.setStyle("-fx-cursor: hand;");
            audioRoot.getChildren().addAll(image, label);
            image.setOnMouseClicked(this::playMusic);
        }
        catch (Exception e){
            audioRoot.getChildren().clear();
        }
    }

    private void playMusic(MouseEvent mouseEvent) {
        var image = new ImageView(stopImg);
        image.setStyle("-fx-cursor: hand;");
        audioRoot.getChildren().clear();
        audioRoot.getChildren().addAll(image, label);

        path = file.getAbsolutePath();
        path = path.replace("\\", "/");
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        stage.setOnCloseRequest(e -> stopMusic());
        image.setOnMouseClicked(e -> stopMusic());
    }

    private void stopMusic() {
        var image = new ImageView(startImg);
        image.setStyle("-fx-cursor: hand;");
        audioRoot.getChildren().clear();
        audioRoot.getChildren().addAll(image, label);
        mediaPlayer.stop();

        image.setOnMouseClicked(this::playMusic);
    }

}
