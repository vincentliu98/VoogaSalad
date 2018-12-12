package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;
import java.util.OptionalDouble;

import static java.lang.Integer.parseInt;

/**
 * This class provides the window for showing the settings for adjusting individual cell sizes.
 *
 * @author Haotian Wang
 */
public class ChangeCellSizeView {
    private Dialog<Pair<Double, Double>> dialog;

    public ChangeCellSizeView(Pair<Double, Double> cellSize) {
        GridPane grid = setUpGridPane();
        TextField width = new TextField();
        width.setText(cellSize.getKey().toString());
        width.setPromptText("Cell Width");
        TextField height = new TextField();
        height.setPromptText("Cell Height");
        height.setText(cellSize.getValue().toString());
        addElements(grid, width, height);
        setUpGrid(grid, width, height);
        setUpGridPane();
    }

    private void setUpGrid(GridPane grid, TextField width, TextField height) {
        dialog = new Dialog<>();
        dialog.setTitle("Resize Individual Cell of the Game");
        ButtonType resizeBtn = new ButtonType("Resize", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(resizeBtn, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            try {
                if (dialogButton == resizeBtn) {
                    return new Pair<>(Double.parseDouble(width.getText()), Double.parseDouble(height.getText()));
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong input values");
                alert.setContentText("Only double values are acceptable");
                alert.showAndWait();
            }
            return null;
        });
    }

    private GridPane setUpGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }

    private void addElements(GridPane grid, TextField width, TextField height) {
        grid.add(new Label("Cell Width of the Grid:"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Cell Height of the Grid:"), 0, 1);
        grid.add(height, 1, 1);
    }

    public Optional<Pair<Double, Double>> showAndWait() {
        return dialog.showAndWait();
    }
}
