package graphUI.phase;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import phase.api.GameEvent;

public class EventTriggerDialog extends Dialog<GameEvent> {
    private GameEvent trigger;
    public EventTriggerDialog() {
        super();
        setDialogPane(new EventTriggerDialogPane());
        setResultConverter(type -> {
            if(type == ButtonType.OK) return trigger;
            else return null;
        });
    }

    private class EventTriggerDialogPane extends DialogPane {
        private VBox root;
        EventTriggerDialogPane() {
            root = new VBox(10);
            var group = new ToggleGroup();
            var clicked = new RadioButton("onClick");
            var keyPressed = new RadioButton("onKeyPress");
            clicked.setToggleGroup(group);
            keyPressed.setToggleGroup(group);
            keyPressed.setOnAction(e -> System.out.println("keypress"));
            clicked.setOnAction(e -> System.out.println("clicked"));
            root.getChildren().addAll(clicked, keyPressed);
        }
    }
}
