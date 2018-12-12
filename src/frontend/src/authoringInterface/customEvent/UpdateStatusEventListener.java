package authoringInterface.customEvent;

/**
 * This interface is implemented by those (currently only StatusView) that listen to status statistic changes
 *
 * @author Haotian Wang
 */
public interface UpdateStatusEventListener<T> {
    /**
     * This method specifies what the event listener will do in events occurring.
     *
     * @param view: The parameter to be passed to the EventListener.
     */
    void setOnUpdateStatusEvent(T view);

    /**
     * This method sets up the view of for the batch mode button
     *
     * @param isShiftDown: A boolean passed from other places that indicate whether Shift is being pressed down.
     */
    void setBatchMode(boolean isShiftDown);

    /**
     * This method sets up the view of the delete mode button.
     *
     * @param isControlDown: A boolean passed from other places that indicate whether Control is being pressed down.
     */
    void setDeleteMode(boolean isControlDown);
}
