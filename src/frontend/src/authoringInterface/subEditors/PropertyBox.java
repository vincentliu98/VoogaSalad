package authoringInterface.subEditors;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.function.Consumer;

/**
 * @author Amy
 */
public class PropertyBox extends HBox {
    private Text keyText, valueText;

    public PropertyBox(String key, String value, Consumer<PropertyBox> deleteThis) {
        setSpacing(20);
        var delete = new Button("X");
        delete.setOnAction(e -> deleteThis.accept(this));
        delete.setStyle("-fx-font-size: 8px;"
                + "-fx-text-fill: white;"
                + "-fx-background-color: black;");
        keyText = new Text(key);
        valueText = new Text(value);
        setStyle("-fx-padding: 5;"
                + "-fx-border-style: dashed");
        getChildren().addAll(delete, keyText, valueText);
    }

    public String getKey() {
        return keyText.getText();
    }

    public String getValue() {
        return valueText.getText();
    }

    public void setValue(String value) {
        valueText.setText(value);
    }
}
