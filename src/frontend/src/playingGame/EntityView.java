package playingGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.function.Consumer;


public class EntityView implements Viewable, PropertyChangeListener {
    public static String REMOVE_KEY = "RemoveEntity";
    public static String CHANGE_IMAGE_KEY = "ChangeImage";
    public static String MOVE_KEY = "MoveEntity";

    private ImageView myImage;
    private Double posX;
    private Double posY;

    private PropertyChangeSupport mySupport;

    public EntityView() {
        mySupport = new PropertyChangeSupport(this);
        myImage = new ImageView();
        posX = 0.0;
        posY = 0.0;
    }

    public EntityView(String imagePath, double xpos, double ypos) {
        mySupport = new PropertyChangeSupport(this);
        myImage = new ImageView(imagePath);
        changeCoordinates(xpos, ypos);
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        mySupport.addPropertyChangeListener(listener);
    }

    @Override
    public ImageView getImageView() {
        return myImage;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(CHANGE_IMAGE_KEY)) {
            changeImage((String) evt.getNewValue());
        } else if (evt.getPropertyName().equals(MOVE_KEY)) {
            changeCoordinates(((Pair<Double, Double>) evt.getNewValue()).getData1(), ((Pair<Double, Double>) evt.getNewValue()).getData2());
        } else if (evt.getPropertyName().equals(REMOVE_KEY)) {
            removeEntity();
        }
    }

    @Override
    public void changeCoordinates(Double xPos, Double yPos) {
        posX = xPos;
        posY = yPos;
        myImage.setX(posX);
        myImage.setY(posY);
    }

    @Override
    public void changeImage(String path) {
        //System.out.println("Imagepath is " + path);
        //Image image = new Image(getClass().getResourceAsStream("/Users/jonathannakagawa/Desktop/Stuff/CompSci308/voogasalad_printstacktrace/src/frontend/resources/square.png"));
        Image image = new Image("square.png");
        myImage.setImage(image);
    }

    @Override
    public void removeEntity() {
        Consumer<DisplayData> cons = (disp) -> {
            disp.removeEntity(this);
        };
        mySupport.firePropertyChange(REMOVE_KEY, this, cons);
    }
}
