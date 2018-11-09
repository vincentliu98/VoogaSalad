package authoringInterface.menu.menuitems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * This class is responsible for the action when the user clicks on the Run menu item.
 *
 * @author Haotian Wang
 */
public class Run extends MenuItemWithAction {
    private static final void handleClick(ActionEvent event) {

    }

    public Run() {
        super("Run", Run::handleClick, Run::handleClick);
    }
}
