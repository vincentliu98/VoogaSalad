package exceptions;

import javafx.scene.control.Alert;

public class ErrorMessage extends Alert {

    public ErrorMessage(ExtendedException exception) {
        super(AlertType.WARNING);
        super.setHeaderText(exception.getMessage());
        super.setContentText(exception.getWarning());
        super.showAndWait();
    }
}