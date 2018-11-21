package gameplay;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.stream.Collectors;

public class Tile extends PropertyHolder<Tile> implements EventHandler<MouseEvent> {
    private int myID;
    private String name;
    private int myWidth, myHeight;
    private int myXCoord, myYCoord; // a hidden assumption here is that the tiles are immovable objects
    private List<String> myImagePaths;
    private List<Integer> myEntities;
    private String myImageSelector; // Groovy codee

    @XStreamOmitField
    private transient SimpleIntegerProperty imgIndex;

    @XStreamOmitField
    private transient List<Image> myImages;

    @XStreamOmitField
    private transient ImageView myImageView;

    /**
     *  Fills out the transient parts
     */
    public void setupView() {
        myImageView = new ImageView();
        myImageView.setPreserveRatio(true);
        myImageView.setOnMouseClicked(this);

        imgIndex = new SimpleIntegerProperty(-1);
        imgIndex.addListener((e, oldVal, newVal) -> myImageView.setImage(myImages.get(newVal.intValue())));
    }

    /**
     *  Adjusts the size of this tile in pixels with respect to screen dimensions
     */
    public void adjustViewSize(double screenWidth, double screenHeight) {
        myImageView.setX((screenWidth*myXCoord)/GameData.gridWidth());
        myImageView.setY((screenHeight*myYCoord)/GameData.gridHeight());
        myImageView.setFitWidth((screenWidth*myWidth)/GameData.gridWidth());
        myImageView.setFitHeight((screenHeight*myHeight)/GameData.gridHeight());

        myImages = myImagePaths.stream()
                               .map(path ->
                                   new Image(
                                       this.getClass().getClassLoader().getResourceAsStream(path),
                                       (screenWidth*myWidth)/GameData.gridWidth(),
                                       (screenHeight*myHeight)/GameData.gridHeight(),
                                       false, true
                                   )
                               ).collect(Collectors.toList());
    }

    /**
     *  Since all image selectors assume that $this refers to THIS specific instance of a tile,
     *  we set the variable $this to this.
     */
    public void updateView() {
        if(!myImageSelector.isEmpty()) {
            GameData.shell().setVariable("$this", this);
            GameData.shell().evaluate(myImageSelector);
            imgIndex.set(Integer.parseInt(GameData.shell().getVariable("$return").toString()));
        } else imgIndex.set(0);
    }

    /**
     *  When the entity moves, it would always call these two methods at the same time
     *
     *  newTile.addEntity(entity.myID)
     *  oldTile.removeEntity(entity.myID)
     */
    public void addEntity(int entityID){
        myEntities.add(entityID);
        GameData.getEntity(entityID).setLocation(myXCoord + (myWidth-1)/2., myYCoord + (myHeight-1)/2);
    }
    public void removeEntity(int entityID) {
        myEntities.remove(entityID);
        // TODO: make sure this removes the OBJECT
        // This method is good enough here.
    }

    public ImageView getImageView(){ return myImageView; }

    public boolean hasNoEntities(){ return myEntities.isEmpty(); }

    public int getID(){ return myID; }
    public String getName() { return name; }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("MouseEvent on tile of id " + myID);
        GameData.addArgument(event, new ClickTag(Tile.class, myID));
    }
}