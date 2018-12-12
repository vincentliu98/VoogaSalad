import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ImageSelectorTest extends Application {
    private Binding sharedVariables;
    private GroovyShell shell;

    private Pane testPane;
    private ImageView view;
    private Image[] imgs;
    private SimpleIntegerProperty imgIndex;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeGroovyShell();
        initializeTestView();

        var myScene = new Scene(testPane, 300, 300);
        primaryStage.setScene(myScene);
        primaryStage.show();


        var frame = new KeyFrame(Duration.millis(2000), e -> tick()); // tick every 2 seconds
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void initializeGroovyShell() {
        sharedVariables = new Binding();
        shell = new GroovyShell(sharedVariables);
        imgIndex = new SimpleIntegerProperty(0);
        sharedVariables.setVariable("imgIndex", imgIndex); // share imgIndex between Java/Groovy
    }

    private void initializeTestView() {
        testPane = new Pane();
        imgs = new Image[]{new Image(this.getClass().getClassLoader().getResourceAsStream("assign.png")),
                new Image(this.getClass().getClassLoader().getResourceAsStream("each.png"))};
        view = new ImageView(imgs[0]);
        view.setX(100);
        view.setY(100);
        testPane.getChildren().add(view);

        imgIndex.addListener((e, oldVal, newVal) ->
                view.setImage(imgs[newVal.intValue()])
        ); // have imgIndex listen to changes to imgIndex
    }

    private void tick() {
        shell.evaluate("imgIndex.set(1-imgIndex.get())"); // change imgIndex within the shell
    }
}
