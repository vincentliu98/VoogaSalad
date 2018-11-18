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
    Map<String, Double> myStats;
    private String myImagePath;
    @XStreamOmitField
    private ImageView myImageView;

    public Entity(int id){
        this.myID = id;
        myStats = new HashMap<>();
        myImagePath = "";
        myImageView = new ImageView();
    }

    public void setPlayer(int playerID){
        this.myPlayerID = playerID;
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

    public void setLocation(double xCoord, double yCoord){
        myImageView.setX(xCoord);
        myImageView.setY(yCoord);
    }

    public void addStat(String key, Double value){
        myStats.put(key, value);
    }

    public int getID(){
        return myID;
    }

    @Override
    public void handle(Event event) {
        GameData.addArgument(new Tag(Entity.class, myID));
    }
}
