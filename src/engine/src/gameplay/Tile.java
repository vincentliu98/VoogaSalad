package gameplay;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tile implements EventHandler {
    private int myID;
    private int myWidth;
    private int myHeight;
    private String myImagePath;
    private Set<Image> myImages;
    private String myImageSelector; // Groovy code
    private List<Integer> myEntities;
    @XStreamOmitField
    private ImageView myImageView;

    public Tile(int id, int width, int height){
        this.myID = id;
        this.myWidth = width;
        this.myHeight = height;
        this.myImagePath = "";
        this.myImages = new HashSet<>();
        this.myImageSelector = "";
        this.myEntities = new ArrayList<>();
        this.myImageView = new ImageView();
    }

    public void addEntity(int entityID){
        myEntities.add(entityID);
        GameData.getEntity(entityID).setLocation(myImageView.getX(), myImageView.getY());
    }

    public void setImagePath(String imagePath) {
        this.myImagePath = imagePath;
        setImageView();
    }

    public void setImageView(){
        if (!myImagePath.isEmpty()){
            myImageView.setImage(new Image(myImagePath));
        }
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