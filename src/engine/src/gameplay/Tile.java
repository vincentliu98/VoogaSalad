package gameplay;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Tile implements EventHandler {
    private int myID;
    private int myWidth;
    private int myHeight;
    private double myXCoord; // TODO: Only temporary; delete later
    private double myYCoord;
    private String myImagePath;
    private List<Integer> myEntities;
    private String myImageSelector; // Groovy code
    private List<String> myImages;
    @XStreamOmitField
    private transient ImageView myImageView;

    public Tile(int id, int width, int height, double xCoord, double yCoord){
        this.myID = id;
        this.myWidth = width;
        this.myHeight = height;
        this.myXCoord = xCoord;
        this.myYCoord = yCoord;
        this.myImagePath = "";
        this.myImageSelector = "";
        this.myImages = new ArrayList<>();
        this.myEntities = new ArrayList<>();
        this.myImageView = null;
    }

    public void addEntity(int entityID){
        myEntities.add(entityID);
        GameData.getEntity(entityID).setLocation(myImageView.getX(), myImageView.getY());
    }

    public void setImagePath(String imagePath) {
        this.myImagePath = imagePath;
    }

    public void setImageView(){
        if (!myImagePath.isEmpty()){
            myImageView = new ImageView();
            myImageView.setImage(new Image(myImagePath));
            myImageView.setPreserveRatio(true);
            myImageView.setFitWidth(100); // TODO: delete later
            myImageView.setX(myXCoord);
            myImageView.setY(myYCoord);
        }
    }

    public ImageView getImageView(){
        return myImageView;
    }

    public void removeEntity(int entityID){
        myEntities.remove(entityID); // TODO: make sure this removes the OBJECT
    }

    public boolean hasNoEntities(){
        return myEntities.isEmpty();
    }

    public int getID(){
        return myID;
    }

    @Override
    public void handle(Event event) {
        GameData.addArgument(new Tag(Tile.class, myID));
    }

    /*
    // IF WE WANT TO USE THE BUILDER PATTERN

    public static class Builder {
        private int width = 0;
        private int height = 0;
        private Set<Image> images = new HashSet<>();
        private String imageSelector = "";

        public Builder(int width, int height){
            this.width = width;
            this.height = height;
        }

        public Builder images(Set<Image> images){
            this.images = images;
            return this;
        }

        public Builder imageSelector(String imageSelector){
            this.imageSelector = imageSelector;
            return this;
        }

        public Tile build(){
            return new Tile(this);
        }
    }

    private Tile(Builder builder){
        myWidth = builder.width;
        myHeight = builder.height;
        myImages = builder.images;
        myImageSelector = builder.imageSelector;
    }*/
}