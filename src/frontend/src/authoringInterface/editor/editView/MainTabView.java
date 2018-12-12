package authoringInterface.editor.editView;

import api.SubView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MainTabView implements SubView<StackPane> {
    public static final int ICON_SIZE = 550;
    private final StackPane addPane;
    private final Image LOGO_IMG =
            new Image(MainTabView.class.getClassLoader().getResourceAsStream("Groove_logo.png"));
    protected Button startBtn;
    private ImageView myIcon;

    public MainTabView() {
        addPane = new StackPane();
        addPane.getStyleClass().add("addPane");
        startBtn = new Button();
        startBtn.setText("START");
        startBtn.getStyleClass().add("startBtn");
        myIcon = new ImageView(LOGO_IMG);
        myIcon.setFitWidth(ICON_SIZE);
        myIcon.setFitHeight(ICON_SIZE);
//        addPane.getChildren().addAll(myIcon, startBtn);
        addPane.getChildren().addAll(myIcon);
        addPane.getStyleClass().add("mainTab");

    }


    @Override
    public StackPane getView() {
        return addPane;
    }

}
