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
    private int tileID;
    private List<String> myImagePaths;
    private String myImageSelector; // Groovy code
    @XStreamOmitField
    private transient SimpleIntegerProperty imgIndex;
    private transient SimpleDoubleProperty xCoord, yCoord;
    @XStreamOmitField
    private transient List<Image> myImages;
    @XStreamOmitField
    private transient ImageView myImageView;

    public Entity(
            int myID,
            int tileID,
            String name,
            Map<String, Object> properties,
            List<String> myImagePaths,
            String myImageSelector
    ) {
        this.myID = myID;
        this.props = properties;
        this.tileID = tileID;
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

        var pos = GameMethods.getTile(tileID);
        xCoord = new SimpleDoubleProperty(pos.getX());
        yCoord = new SimpleDoubleProperty(pos.getY());
    }

    /**
     *  Adjusts the size of this tile in pixels with respect to screen dimensions
     *  TODO: test whether "addListener" replaces the old one
     */
    public void adjustViewSize(double screenWidth, double screenHeight) {
        myImageView.setY((screenHeight * yCoord.get()) / GameMethods.gridHeight());
        myImageView.setX((screenWidth * xCoord.get()) / GameMethods.gridWidth());
        myImageView.setFitWidth(screenWidth/GameMethods.gridWidth());
        myImageView.setFitHeight(screenHeight/GameMethods.gridHeight());

        myImages = myImagePaths.stream()
                               .map(path ->
                                   new Image(
                                       this.getClass().getClassLoader().getResourceAsStream(path),
                                       screenWidth/GameMethods.gridWidth(), screenHeight/GameMethods.gridHeight(),
                                       false, true
                                   )
                               ).collect(Collectors.toList());

        xCoord.addListener((e, oldVal, newVal) -> {
            myImageView.setX(screenWidth * newVal.doubleValue() / GameMethods.gridWidth());
        });

        yCoord.addListener((e, oldVal, newVal) -> {
            myImageView.setY(screenHeight * newVal.doubleValue() / GameMethods.gridHeight());
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

    public void setLocation(int tileID){
        this.tileID = tileID;
        var data = GameMethods.getTile(tileID);
        this.xCoord.set(data.getX());
        this.yCoord.set(data.getY());
    }

    public ImageView getImageView(){ return myImageView; }
    public int getID(){ return myID; }
    public String getName() { return name; }
    public double getX() { return xCoord.get(); }
    public double getY() { return yCoord.get(); }
    public int getTileID() { return tileID; }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("MouseEvent from entity of id " + myID);
        GameData.addArgument(event, new ClickTag(Entity.class, myID));
    }
}
