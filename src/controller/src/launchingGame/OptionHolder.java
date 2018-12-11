package launchingGame;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.beans.PropertyChangeSupport;

public class OptionHolder extends HBox {
    public static final String CHANGE_EVENT_KEY = "Selected";
    public static final String TEXT_CSS = "text-option";
    public static final String CONTAINER_CSS = "text-option-holder";
    public static final String SELECT_CSS = "-fx-border-width: 0 0 2 0";
    public static final String DESELECT_CSS = "-fx-border-width: 0 0 0 0";

    private PropertyChangeSupport mySupport;

    private Text myText;

    public OptionHolder(String string) {
        mySupport = new PropertyChangeSupport(this);
        initContainer();
        initText(string);
    }

    public void addListener(TextOptions option) {
        mySupport.addPropertyChangeListener(CHANGE_EVENT_KEY, option);
    }

    public void select() {
        setStyle(SELECT_CSS);
    }

    public void deselect() {
        setStyle(DESELECT_CSS);
    }

    private void initText(String string) {
        myText = new Text(string);
        myText.getStyleClass().add(TEXT_CSS);
        myText.setFill(Color.WHITE);
        getChildren().add(myText);
    }

    public void setOnClickListener(EventHandler eventHandler) {
        myText.setOnMouseClicked(eventHandler);
    }

    private void initContainer() {
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().add(CONTAINER_CSS);

        setOnMouseClicked(event -> {
            System.out.println("world");
            mySupport.firePropertyChange(CHANGE_EVENT_KEY, this, this);
        });
    }

}
