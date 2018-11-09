package authoringInterface.menu.menuitems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * This class is responsible for the action when the user clicks on the Open menu item.
 *
 * @author Haotian Wang
 */
public class Open extends MenuItemWithAction {
    private static final void handleClick(ActionEvent event) {
        
    }

    public Open() {
        super("Open", Open::handleClick, Open::handleClick);
    }
}
