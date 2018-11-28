package authoring;


import com.thoughtworks.xstream.annotations.XStreamOmitField;
import gameObjects.GameObjectsCRUDInterface;
import gameObjects.SimpleGameObjectsCRUD;
import groovy.api.GroovyFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import phase.api.PhaseDB;

/**
 *  This class contains all the tools to author a game;
 */
public class AuthoringTools {
    private GameObjectsCRUDInterface entityDB;
    private PhaseDB phaseDB;

    @XStreamOmitField
    private transient GroovyFactory factory;

    public AuthoringTools(int gridWidth, int gridHeight) {
        factory = new GroovyFactory();

        entityDB = new SimpleGameObjectsCRUD(gridWidth, gridHeight);

        phaseDB = new PhaseDB(factory);
    }

    public void setGridDimension(int width, int height) {
        entityDB.setDimension(width, height);
    }

    public GroovyFactory factory() { return factory; }
    public GameObjectsCRUDInterface entityDB() { return entityDB; }
    public PhaseDB phaseDB() { return phaseDB; }

    public String toEngineXML() {
        String notNatural = "\\$NOBODY\\$IS\\$GONNA\\$WRITE\\$THIS";
        return Serializers.forEngine()
                          .toXML(this)
                          .replaceAll("&lt; ", "<"+notNatural)
                          .replaceAll("&gt; ", ">"+notNatural)
                          .replaceAll("&lt;= ", "<="+notNatural)
                          .replaceAll("&gt;= ", ">="+notNatural)
                          .replaceAll("&lt;", "<")
                          .replaceAll("&gt;", ">")
                          .replaceAll("<"+notNatural, "&lt; " )
                          .replaceAll(">"+notNatural, "&gt; " )
                          .replaceAll("<="+notNatural, "&lt;= ")
                          .replaceAll(">="+notNatural, "&gt;= ");
    }
    public String toAuthoringXML() { return null; }
}
