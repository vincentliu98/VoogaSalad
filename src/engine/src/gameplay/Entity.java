package gameplay;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class Entity implements EventHandler {
    private int myPlayerID;
    private int myID;
    private double myXCoord; // TODO: Only temporary; delete later
    private double myYCoord;
    Map<String, Double> myStats;
    private String myImagePath;
    @XStreamOmitField
    private ImageView myImageView;

    public Entity(int id){
        this.myID = id;
        myStats = new HashMap<>();
        myImagePath = "";
        myXCoord = -1; //FIXME: temporary default values only
        myYCoord = -1;
    }

    public void setPlayer(int playerID){
        this.myPlayerID = playerID;
    } // FIXME: is this necessary?

    public void setImagePath(String imagePath) {
        this.myImagePath = imagePath;
    }

    public ImageView getImageView(){
        return myImageView;
    }

    public void setImageView(){
        if (!myImagePath.isEmpty()){
            myImageView = new ImageView();
            myImageView.setImage(new Image(myImagePath)); // FIXME: shouldn't make new Image just for changing location
            myImageView.setPreserveRatio(true);
            myImageView.setFitWidth(100); // TODO: delete later
        }
        if (myXCoord != -1 && myYCoord != -1){
            myImageView.setX(myXCoord);
            myImageView.setY(myYCoord);
        }
    }

    public void setLocation(double xCoord, double yCoord){
        myXCoord = xCoord;
        myYCoord = yCoord;
        setImageView();
    }

    public void addStat(String key, Double value){
        myStats.put(key, value);
    }

    public int getID(){
        return myID;
    }

    @Override
    public void handle(Event event) {
        System.out.println("handle called by Entity of id " + myID);
        GameData.addArgument(new Tag(Entity.class, myID));
    }
}
