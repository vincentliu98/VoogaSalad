package gameplay;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import grids.PointImpl;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Entity extends PropertyHolder<Entity> implements GameObject, EventHandler<MouseEvent> {
    private int myID;
    private String name;
    private String instanceName;
    private List<String> myImagePaths;
    private String myImageSelector; // Groovy code
    private int myWidth, myHeight;
    private PointImpl myCoord; // ugh interfaces are hard to use with XStream

    @XStreamOmitField
    private transient SimpleIntegerProperty imgIndex;
    private transient SimpleDoubleProperty xCoord, yCoord;
    @XStreamOmitField
    private transient List<Image> myImages;
    @XStreamOmitField
    private transient ImageView myImageView;

    public Entity(
            int myID,
            int x, int y,
            int width, int height,
            String name,
            Map<String, Object> properties,
            List<String> myImagePaths,
            String myImageSelector
    ) {
        this.myID = myID;
        this.props = properties;
        this.myCoord = new PointImpl(x, y);
        this.myWidth = width;
        this.myHeight = height;
        this.name = name;
        this.myImagePaths = myImagePaths;
        this.myImageSelector = myImageSelector;
        setupView();
    }

    /**
     * Fills out the transient parts
     */
    public void setupView() {
        loadImages(true);
        myImageView = new ImageView();
        myImageView.setPreserveRatio(false);
        myImageView.setOnMouseClicked(this);

        imgIndex = new SimpleIntegerProperty(-1);
        imgIndex.addListener((e, oldVal, newVal) -> myImageView.setImage(myImages.get(newVal.intValue())));

        this.xCoord = new SimpleDoubleProperty(myCoord.getX());
        this.yCoord = new SimpleDoubleProperty(myCoord.getY());
    }

    /**
     * Adjusts the size of this tile in pixels with respect to screen dimensions
     * TODO: test whether "addListener" replaces the old one
     */
    public void adjustViewSize(double screenWidth, double screenHeight) {
        myImageView.setX((screenWidth * xCoord.get()) / GameMethods.gridWidth());
        myImageView.setY((screenHeight * yCoord.get()) / GameMethods.gridHeight());
        myImageView.setFitWidth(screenWidth * myWidth / GameMethods.gridWidth());
        myImageView.setFitHeight(screenHeight * myHeight / GameMethods.gridHeight());

        loadImages(false);

        xCoord.addListener((e, oldVal, newVal) -> {
            myImageView.setX(screenWidth * newVal.doubleValue() / GameMethods.gridWidth());
        });

        yCoord.addListener((e, oldVal, newVal) -> {
            myImageView.setY(screenHeight * newVal.doubleValue() / GameMethods.gridHeight());
        });

        updateView();
    }

    private void loadImages(boolean useNaturalImageSize) {
        myImages = new ArrayList<>();
        for (var path : myImagePaths) {
            Image img;
            if(useNaturalImageSize) img = new Image(Objects.requireNonNull(PathUtility.getResourceAsStream(path)));
            else img = new Image(Objects.requireNonNull(PathUtility.getResourceAsStream(path)), myImageView.getFitWidth(), myImageView.getFitHeight(), false, true);
            myImages.add(img);
        }
    }

    /**
     * Since all image selectors assume that $this refers to THIS specific instance of a tile,
     * we set the variable $this to this.
     */
    public void updateView() {
        if (!myImageSelector.isEmpty()) {
            GameData.shell().setVariable("$this", this);
            GameData.shell().evaluate(myImageSelector);
            imgIndex.set(Integer.parseInt(GameData.shell().getVariable("$return").toString()));
        } else imgIndex.set(0);
    }

    public void setLocation(double x, double y) {
        this.xCoord.set(x);
        this.yCoord.set(y);
    }

    public ImageView getImageView() {
        return myImageView;
    }

    public int getID() {
        return myID;
    }

    public String getName() {
        return name;
    }

    public String getInstanceName() { return instanceName; }

    public double getX() {
        return xCoord.get();
    }

    public double getY() {
        return yCoord.get();
    }

    @Override
    public double getWidth() {
        return myWidth;
    }

    @Override
    public double getHeight() {
        return myHeight;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("MouseEvent from entity of id " + myID);
        GameData.addArgument(event, new ClickTag(Entity.class, myID));
    }
}
