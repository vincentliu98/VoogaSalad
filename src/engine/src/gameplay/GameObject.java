package gameplay;

import javafx.scene.image.ImageView;

public interface GameObject {
    ImageView getImageView();

    int getID();

    String getName();

    double getX();

    double getY();

    double getWidth();

    double getHeight();
}
