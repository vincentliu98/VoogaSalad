package launchingGame;

import api.SubView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class SearchBar implements SubView<HBox> {
    public static final String PROMPT_MESSAGE = "Game Name";
    public static final String CROSS_PATH = "/graphics/black-cross.png";
    public static final double BAR_WIDTH = 400;

    private HBox myBox;

    private TextField myTextField;
    private Button myCloseButton;

    public SearchBar(){
        initField();
        initButton();
        initBox();

        myBox.getChildren().add(myCloseButton);
        myBox.getChildren().add(myTextField);
    }

    private void initBox(){
        myBox = new HBox();
        myBox.setAlignment(Pos.CENTER_LEFT);

    }

    private void initField(){
        myTextField = new TextField();
        myTextField.setPromptText(PROMPT_MESSAGE);

        myTextField.getStyleClass().add("search-bar");

        myTextField.setPrefWidth(BAR_WIDTH);
    }

    private void initButton(){
        Image image = new Image(getClass().getResourceAsStream(CROSS_PATH));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(10);
        imageView.setFitHeight(10);

        myCloseButton = new Button();
        myCloseButton.setGraphic(imageView);

        myCloseButton.getStyleClass().add("closebutton");

        myCloseButton.setOnAction(event -> {
            myTextField.clear();
        });
    }

    @Override
    public HBox getView() {
        return myBox;
    }
}
