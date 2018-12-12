package gameplay;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import grids.PointImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tile extends PropertyHolder<Tile> implements GameObject, EventHandler<MouseEvent> {
    private int myID;
    private String name;
    private String instanceName;
    private int myWidth, myHeight;
    private PointImpl myCoord; // ugh interfaces are hard to use with XStream
    private List<String> myImagePaths;
    private String myImageSelector; // Groovy codee

    @XStreamOmitField
    private transient SimpleIntegerProperty imgIndex;

    @XStreamOmitField
    private transient List<Image> myImages;

    @XStreamOmitField
    private transient ImageView myImageView;

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
    }

    /**
     * Adjusts the size of this tile in pixels with respect to screen dimensions
     */
    public void adjustViewSize(double screenWidth, double screenHeight) {
        myImageView.setX((screenWidth * myCoord.getX()) / GameMethods.gridWidth());
        myImageView.setY((screenHeight * myCoord.getY()) / GameMethods.gridHeight());
        myImageView.setFitWidth((screenWidth * myWidth) / GameMethods.gridWidth());
        myImageView.setFitHeight((screenHeight * myHeight) / GameMethods.gridHeight());

        loadImages(false);

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
            var ret = Integer.parseInt(GameData.shell().getVariable("$return").toString());
            imgIndex.set(ret);
        } else imgIndex.set(0);
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

    @Override
    public double getX() {
        return myCoord.getX();
    }

    @Override
    public double getY() {
        return myCoord.getY();
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
        System.out.println("MouseEvent on tile of id " + myID);
        GameData.addArgument(event, new ClickTag(Tile.class, myID));
    }
}