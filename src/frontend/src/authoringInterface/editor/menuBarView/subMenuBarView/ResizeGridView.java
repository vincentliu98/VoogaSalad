package authoringInterface.editor.menuBarView.subMenuBarView;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

import static java.lang.Integer.parseInt;

/**
 * @author Amy
 */
public class ResizeGridView {
    private Dialog<Pair<Integer, Integer>> dialog;

    public ResizeGridView(Pair<Integer, Integer> gridDimension) {
        GridPane grid = setUpGridPane();
        TextField col = new TextField();
        col.setPromptText("Column");
        TextField row = new TextField();
        row.setPromptText("Row");

        row.setText(gridDimension.getKey().toString());
        col.setText(gridDimension.getValue().toString());

        addElements(grid, col, row);

        setUpGrid(grid, col, row);
    }

    private void setUpGrid(GridPane grid, TextField col, TextField row) {
        dialog = new Dialog<>();
        dialog.setTitle("Resize Grid of the Game");
        dialog.setHeaderText("This action will remove all entities on the grid.");
        ButtonType resizeBtn = new ButtonType("Resize", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(resizeBtn, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            try {
                if (dialogButton == resizeBtn) {
                    return new Pair<>(parseInt(col.getText()), parseInt(row.getText()));
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong input values");
                alert.setContentText("Only Integer values are acceptable");
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

    private void addElements(GridPane grid, TextField col, TextField row) {
        grid.add(new Label("Column of the Grid:"), 0, 0);
        grid.add(col, 1, 0);
        grid.add(new Label("Row of the Grid:"), 0, 1);
        grid.add(row, 1, 1);
    }

    public Optional<Pair<Integer, Integer>> showAndWait() {
        return dialog.showAndWait();
    }
}