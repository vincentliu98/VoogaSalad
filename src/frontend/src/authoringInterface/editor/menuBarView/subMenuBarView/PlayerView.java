package authoringInterface.editor.menuBarView.subMenuBarView;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Optional;

public class PlayerView {
    private Dialog dialog;

    public PlayerView() {
        dialog = new Dialog();
        dialog.setTitle("Players");
        dialog.setHeaderText("How many players in this game?");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.APPLY);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        dialog.getDialogPane().setContent(grid);
        grid.add(new Label("The number of Players:"), 0, 0);
        TextField col = new TextField();
        col.setPromptText("0");
        grid.add(col, 1, 0);

    }

    public Optional showAndwait() {
        return dialog.showAndWait();
    }
}
