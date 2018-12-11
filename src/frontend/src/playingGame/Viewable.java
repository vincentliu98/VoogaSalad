package playingGame;

import javafx.scene.image.ImageView;

import java.beans.PropertyChangeListener;

public interface Viewable {

    /**
     * This method changes the image that the viewable displays
     *
     * @param path The path to the new image
     */
    void changeImage(String path);

    /**
     * This method changes the position of the viewable
     *
     * @param xPos The new x position
     * @param yPos The new y position
     */
    void changeCoordinates(Double xPos, Double yPos);

    /**
     * This method get the Imageview stored in the viewable
     *
     * @return The stored Imageview
     */
    ImageView getImageView();

    /**
     * This method provides a consumer to remove the entity from the current javafx scene
     *
     * @return
     */
    void removeEntity();

    /**
     * This method adds a PropertyChangeListener to the viewable
     *
     * @param listener The java beans listener to be added
     */
    void addListener(PropertyChangeListener listener);
}
