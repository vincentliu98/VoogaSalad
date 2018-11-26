package gameplay;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Entity extends PropertyHolder<Entity> implements GameObject, EventHandler<MouseEvent> {
    private int myID;

    private String name;

    // A hidden assumption here is that each entity can only cover one tile.
    // * These exist solely because I'm not sure whether xstream can correctly
    // * serialize SimpleIntegerProperties
    private double myXCoord, myYCoord;

    private List<String> myImagePaths;

    private String myImageSelector; // Groovy codee

    @XStreamOmitField
    private transient SimpleIntegerProperty imgIndex;
    private transient SimpleDoubleProperty xCoord, yCoord;

    @XStreamOmitField
    private transient List<Image> myImages;

    @XStreamOmitField
    private transient ImageView myImageView;

    public Entity(
        int myID,

        String name,
        Map<String, Object> properties,
        List<String> myImagePaths,
        String myImageSelector
    ) {
        this.myID = myID;
        this.props = properties;
        this.name = name;
        this.myImagePaths = myImagePaths;
        this.myImageSelector = myImageSelector;
        setupView();
    }

    /**
     *  Fills out the transient parts
     */
    public void setupView() {
        myImageView = new ImageView();
        myImageView.setPreserveRatio(true);
        myImageView.setOnMouseClicked(this);

        imgIndex = new SimpleIntegerProperty(-1);
        imgIndex.addListener((e, oldVal, newVal) -> myImageView.setImage(myImages.get(newVal.intValue())));

        xCoord = new SimpleDoubleProperty(myXCoord);
        yCoord = new SimpleDoubleProperty(myYCoord);
    }

    /**
     *  Adjusts the size of this tile in pixels with respect to screen dimensions
     *  TODO: test whether "addListener" replaces the old one
     */
    public void adjustViewSize(double screenWidth, double screenHeight) {
        myImageView.setY((screenHeight * yCoord.get()) / GameData.gridHeight());
        myImageView.setX((screenWidth * xCoord.get()) / GameData.gridWidth());
        myImageView.setFitWidth(screenWidth/GameData.gridWidth());
        myImageView.setFitHeight(screenHeight/GameData.gridHeight());

        myImages = myImagePaths.stream()
                               .map(path ->
                                   new Image(
                                       this.getClass().getClassLoader().getResourceAsStream(path),
                                       screenWidth/GameData.gridWidth(), screenHeight/GameData.gridHeight(),
                                       false, true
                                   )
                               ).collect(Collectors.toList());

        xCoord.addListener((e, oldVal, newVal) -> {
            myImageView.setX(screenWidth * newVal.doubleValue() / GameData.gridWidth());
            myXCoord = newVal.intValue();
        });

        yCoord.addListener((e, oldVal, newVal) -> {
            myImageView.setY(screenHeight * newVal.doubleValue() / GameData.gridHeight());
            myYCoord = newVal.intValue();
        });

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
     *  This method should be called by Tile.addEntity, not from anywhere else
     */
    public void setLocation(double xCoord, double yCoord){
        this.xCoord.set(xCoord);
        this.yCoord.set(yCoord);
    }

    public ImageView getImageView(){ return myImageView; }
    public int getID(){ return myID; }
    public String getName() { return name; }
    public double getX() { return xCoord.get(); }
    public double getY() { return yCoord.get(); }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("MouseEvent from entity of id " + myID);
        GameData.addArgument(event, new ClickTag(Entity.class, myID));
    }
}
