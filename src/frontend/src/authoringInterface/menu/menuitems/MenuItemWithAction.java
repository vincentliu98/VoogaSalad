package authoringInterface.menu.menuitems;

import api.SubView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

/**
 * This class serves as a slight modification to JavaFx's MenuItem Node, because it forces the user to specify the action associated with clicking on the menu item or on validation.
 *
 * @author Haotian Wang
 */
public class MenuItemWithAction {
    /**
     * The MenuItem JavaFx Node associated with this MenuItemAction object.
     */
    private MenuItem menuItem;

    /**
     * The EventHandler that is used to handle action of clicking on the menu.
     */
    private EventHandler<ActionEvent> handleClick;

    /**
     * The EventHandler that is used to handle when the user presses short cut to reach the menu item.
     */
    private EventHandler<ActionEvent> handleShortCut;

    public MenuItemWithAction(String text, EventHandler<ActionEvent> click, EventHandler<ActionEvent> shortcut) {
        menuItem = new MenuItem(text);
        handleClick = click;
        handleShortCut = shortcut;
    }

    /**
     * @return The MenuItem JavaFx Node.
     */
    public MenuItem getMenuItem() {
        return menuItem;
    }

    /**
     * @return The EventHandler associated with clicking on the menu item.
     */
    public EventHandler<ActionEvent> getClickHandler() {
        return handleClick;
    }

    /**
     * @return The EventHandler associated with using shortcut to reach the menu item.
     */
    public EventHandler<ActionEvent> getShorCutHandler() {
        return handleShortCut;
    }
}
