package gameplay;

import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;

public class Tile {

    // PLACEHOLDER CLASS

    private int myWidth;
    private int myHeight;
    private Set<Image> myImages;
    private String myImageSelector; // Groovy code

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
    }
}