package gameplay;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private int myID;
    private int myWidth;
    private int myHeight;
    private double myXCoord; // TODO: Only temporary; delete later
    private double myYCoord;
    private String myImagePath;
    private List<Integer> myEntities;
    private String myImageSelector; // Groovy codee
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

    public void addEntity(int entityID, Group root){
        myEntities.add(entityID);
        GameData.getEntity(entityID).setLocation(myXCoord, myYCoord);
        root.getChildren().add(GameData.getEntity(entityID).getImageView());
    }

    public String getImagePath(){
        return myImagePath;
    }

    public double getXCoord(){
        return myXCoord;
    }

    public double getYCoord(){
        return myYCoord;
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
            myImageView.setOnMouseClicked(event -> {
                GameData.addArgument(new Tag(Tile.class, myID));
            });
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