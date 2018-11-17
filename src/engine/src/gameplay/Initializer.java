package gameplay;

import java.io.File;
import java.util.Set;

public class Initializer {
    XMLParser myXMLParser;

    public Initializer(File file){
        myXMLParser = new XMLParser();
        initGame(file);
    }

    private void initGame(File file){
        myXMLParser.loadFile(file);
        // Then, call getGlobalData(), getEntities(), etc.
    }

}