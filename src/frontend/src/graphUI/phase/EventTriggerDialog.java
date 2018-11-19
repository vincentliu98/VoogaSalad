package graphUI.phase;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import phase.api.GameEvent;

public class EventTriggerDialog extends Dialog<GameEvent> {
    private static final double WIDTH = 200;
    private static final double HEIGHT = 400;
    private GameEvent trigger;
    private ButtonType customOK;
    private ButtonType customCANCEL;
    public EventTriggerDialog() {
        super();
        customOK = new ButtonType("OK");
        customCANCEL = new ButtonType("CANCEL");
        setDialogPane(new EventTriggerDialogPane());
        setResultConverter(type -> {
            if(type == customOK) return trigger;
            else return null;
        });
    }

    private class EventTriggerDialogPane extends DialogPane {
        private VBox root;
        EventTriggerDialogPane() {
            setPrefWidth(WIDTH);
            setPrefWidth(HEIGHT);
            root = new VBox(10);
            var group = new ToggleGroup();
            var clicked = new RadioButton("onClick");
            var keyPressed = new RadioButton("onKeyPress");
            var text = new Text("press on the key to listen to");
            var pressed = new Text();
            clicked.setToggleGroup(group);
            keyPressed.setToggleGroup(group);
            keyPressed.setOnAction(e -> {
                text.setVisible(true);
                setOnKeyReleased(ev -> {
                    pressed.setText(ev.getCode().toString());
                    trigger = GameEvent.keyPress(ev.getCode());
                });
            });
            clicked.setOnAction(e -> {
                setOnKeyReleased(null);
                trigger = GameEvent.mouseClick();
                text.setVisible(false);
                pressed.setText("");
            });
            getButtonTypes().addAll(
                customOK,
                customCANCEL
            );

            addEventFilter(KeyEvent.KEY_RELEASED, e -> {
                if(e.getCode() == KeyCode.SPACE) {
                    trigger = GameEvent.keyPress(e.getCode());
                    pressed.setText(e.getCode().toString());
                }
            });
            addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                if(e.getCode() == KeyCode.SPACE) {
                    trigger = GameEvent.keyPress(e.getCode());
                    pressed.setText(e.getCode().toString());
                }
            });

            root.getChildren().addAll(clicked, keyPressed, text, pressed);
            setContent(root);
        }
    }
}
