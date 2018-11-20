package authoringInterface.editor.editView;

import api.SubView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class AddTabView implements SubView<StackPane> {
    private final StackPane addPane;
    public static final int ICON_SIZE = 550;
    private final Image LOGO_IMG =
            new Image(AddTabView.class.getClassLoader().getResourceAsStream("Groove_logo.png"));
    private ImageView myIcon;

    public AddTabView() {
        addPane = new StackPane();
        addPane.getStyleClass().add("addPane");
        var logo = LOGO_IMG;
        myIcon = new ImageView(logo);
        myIcon.setFitWidth(ICON_SIZE);
        myIcon.setFitHeight(ICON_SIZE);
        addPane.getChildren().add(myIcon);
    }


    @Override
    public StackPane getView() {
        return addPane;
    }

}
