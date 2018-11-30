package authoringInterface.customEvent;


import javafx.scene.Node;

/**
 * This interface is implemented by those (currently only StatusView) that listen to status statistic changes
 *
 * @author Haotian Wang
 */
@FunctionalInterface
public interface UpdateStatusEventListener<T extends Node> {
    /**
     * This method specifies what the event listener will do in events occurring.
     */
    void setOnUpdateStatusEvent();
}
