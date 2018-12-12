package authoringInterface.subEditors;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Get prop's key and value
 *
 * @author Amy
 */

public class PropertyInputDialog extends Dialog<Pair<String, String>> {
    private TextField key, value;

    public PropertyInputDialog() {
        setTitle("Property of Entity");
        setHeaderText("This action will add new property of the entity.");

        var grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 10, 10, 20));
        key = new TextField();
        value = new TextField();
        key.setMinWidth(80);
        key.setPromptText("i.e) hp");
        value.setMinWidth(80);
        value.setPromptText("i.e) 5");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(key, 1, 0);
        grid.add(new Label("Value:"), 0, 1);
        grid.add(value, 1, 1);

        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        getDialogPane().setContent(grid);

        setResultConverter(type -> {
            if (type == ButtonType.APPLY) {
                return new Pair<>(key.getText(), value.getText());
            } else return null;
        });
    }
}
