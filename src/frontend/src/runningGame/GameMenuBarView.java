package runningGame;

import api.SubView;
import javafx.scene.control.MenuBar;

/**
 * This is the menu bar for the running game window.
 *
 * @author Haotian Wang
 */
public class GameMenuBarView implements SubView<MenuBar> {
    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public MenuBar getView() {
        return null;
    }
}
