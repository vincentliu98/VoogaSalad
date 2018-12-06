package authoringInterface.toolbar;

import api.SubView;
import javafx.scene.control.ToolBar;

/**
 * This class contains a floating ToolBar control that provides editing functionality to many elements on the authoring Grid. Example functions include deleting, editing and many others.
 *
 * @author Haotian Wang
 */
public class GridToolBar implements SubView<ToolBar> {
    private ToolBar toolBar;

    public GridToolBar() {
        toolBar = new ToolBar();
    }

    @Override
    public ToolBar getView() {
        return toolBar;
    }
}
