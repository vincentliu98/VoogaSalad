package social;

import exceptions.ErrorMessage;
import exceptions.ExtendedException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;


public abstract class Icon {
    public static final String TEXT_CSS = "title-box";
    public static final String DESCRIPTION_CSS = "description-box";
    public static final double DESCRIPTION_INSET = 10;
    public static final String BUTTON_CSS_NORMAL = "play-button-normal";
    public static final double BUTTON_WIDTH = 110;
    public static final double BUTTON_HEIGHT = 35;
    public static final String BUTTON_CSS_HOVER = "play-button-hover";
    public static final String BUTTON_HOLDER_CSS = "button-holder";
    public static final double ICON_WIDTH = 250;
    public static final double ICON_HEIGHT = 180;
    public static final String SPACE_REGEX = "[ \\t]+";
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");


    public static String BUTTON_TEXT;
    public static String IMAGES_FOLDER_PATH;

    protected StackPane myPane;
    protected ImageView myBackground;
    protected HBox myTitleHolder;
    protected Text myTitle;
    protected HBox myDescriptionHolder;
    protected Text myDescription;
    protected Button myButton;
    protected HBox myButtonHolder;
    protected User myUser;

    protected String myName;
    protected String myDescriptionString;
    protected String myImagePath;
    protected String[] myTags;
    protected String myReferencePath;


    public Icon(String gameName, String description, String reference, String color, String imagePath,
                String tags, User user, String buttonText, String imgFolderPath) {
        myName = gameName;
        myDescriptionString = description;
        myReferencePath = reference;
        myImagePath = imagePath;
        myTags = tags.split(SPACE_REGEX);
        myUser = user;
        BUTTON_TEXT = buttonText;
        IMAGES_FOLDER_PATH = imgFolderPath;

        initPane();
        initBackground();
        initTitle();
        initDescription();
        initButton();
        initButtonHandlers();
    }

    public abstract void initButtonHandlers();

    public Boolean checkTag(String tag) {
        if (myName.equals(tag)) {
            return true;
        }
        for (String tg : myTags) {
            if (tg.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkName(String name) {
        if (myName.equals(name)) return true;
        return false;
    }

    protected void initPane() {
        myPane = new StackPane();
        myPane.setOnMouseEntered(event -> {
            myPane.getChildren().remove(myTitleHolder);
            myPane.getChildren().add(myDescriptionHolder);
            myPane.getChildren().add(myButtonHolder);
        });
        myPane.setOnMouseExited(event -> {
            myPane.getChildren().remove(myDescriptionHolder);
            myPane.getChildren().remove(myButtonHolder);
            myPane.getChildren().add(myTitleHolder);
        });

    }

    public void initBackground() {
        try{
            Image image = new Image(new FileInputStream(IMAGES_FOLDER_PATH + myImagePath));
            myBackground = new ImageView(image);
            myBackground.setFitWidth(ICON_WIDTH);
            myBackground.setFitHeight(ICON_HEIGHT);
            myPane.getChildren().add(myBackground);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            new ErrorMessage(new ExtendedException(myErrors.getString("FileDoesNotExist"), myErrors.getString(
                    "FileDoesNotExistWarning")));
        }
    }

    public void initTitle() {
        myTitle = new Text(myName);
        myTitle.setFill(Color.BLACK);
        myTitleHolder = new HBox();
        myTitleHolder.getChildren().add(myTitle);
        myTitleHolder.setAlignment(Pos.BOTTOM_LEFT);
        myTitleHolder.getStyleClass().add(TEXT_CSS);
        myPane.getChildren().add(myTitleHolder);
    }

    protected void initDescription() {
        myDescriptionHolder = new HBox();
        myDescriptionHolder.setAlignment(Pos.CENTER);
        myDescriptionHolder.getStyleClass().add(DESCRIPTION_CSS);
        myDescription = new Text(myDescriptionString);
        myDescription.setTextAlignment(TextAlignment.LEFT);
        myDescription.setFill(Color.BLACK);
        myDescription.setWrappingWidth(ICON_WIDTH - DESCRIPTION_INSET);
        myDescriptionHolder.getChildren().add(myDescription);

    }

    protected void initButton() {
        myButton = new Button(BUTTON_TEXT);
        myButton.setTextFill(Color.WHITE);
        myButton.getStyleClass().add(BUTTON_CSS_NORMAL);
        myButton.setPrefWidth(BUTTON_WIDTH);
        myButton.setPrefHeight(BUTTON_HEIGHT);
        myButtonHolder = new HBox();
        myButtonHolder.getChildren().add(myButton);
        myButtonHolder.setAlignment(Pos.BOTTOM_LEFT);
        myButtonHolder.getStyleClass().add(BUTTON_HOLDER_CSS);
    }

    public String getName() {
        return myName;
    }

    public StackPane getView() {
        return myPane;
    }

}

