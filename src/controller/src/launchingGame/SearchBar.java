package launchingGame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import social.EngineEvent;
import social.EventBus;

public class SearchBar {
    public static final String PROMPT_MESSAGE = "Game Name";
    public static final String CROSS_PATH = "/graphics/black-cross.png";
    public static final double BAR_WIDTH = 400;
    public static final double MIN_WIDTH = 200;
    public static final double ICON_WIDTH = 10;
    public static final double ICON_HEIGHT = 10;


    private HBox myBox;

    private TextField myTextField;
    private Button myCloseButton;
    private Searchable mySearchable;

    public SearchBar(Searchable searchable) {
        mySearchable = searchable;

        initField();
        initButton();
        initBox();

        myBox.getChildren().add(myCloseButton);
        myBox.getChildren().add(myTextField);
        EventBus.getInstance().register(EngineEvent.SWITCH_SEARCHABLE, this::switchSearchable);
    }

    private void initBox() {
        myBox = new HBox();
        myBox.setAlignment(Pos.CENTER_LEFT);

    }

    private void initField() {
        myTextField = new TextField();
        myTextField.setPromptText(PROMPT_MESSAGE);

        myTextField.getStyleClass().add("search-bar");

        myTextField.setPrefWidth(BAR_WIDTH);
        myTextField.setMinWidth(MIN_WIDTH);


        myTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String txt = myTextField.getCharacters().toString();
                mySearchable.showByTag(txt);
                myTextField.clear();
            }
        });
    }

    private void initButton() {
        Image image = new Image(getClass().getResourceAsStream(CROSS_PATH));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(ICON_WIDTH);
        imageView.setFitHeight(ICON_HEIGHT);

        myCloseButton = new Button();
        myCloseButton.setGraphic(imageView);

        myCloseButton.getStyleClass().add("closebutton");

        myCloseButton.setOnAction(event -> {
            myTextField.clear();
            mySearchable.showAll();
        });
    }

    public void switchSearchable(Object ...args){
        Searchable searchable = (Searchable) args[0];
        mySearchable = searchable;
    }

    public HBox getView() {
        return myBox;
    }
}
