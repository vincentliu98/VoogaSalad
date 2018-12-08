package authoringInterface.customEvent;

/**
 * This interface is implemented by those (currently only StatusView) that listen to status statistic changes
 *
 * @author Haotian Wang
 */
@FunctionalInterface
public interface UpdateStatusEventListener<T> {
    /**
     * This method specifies what the event listener will do in events occurring.
     *
     * @param view: The parameter to be passed to the EventListener.
     */
    void setOnUpdateStatusEvent(T view);
}
