package authoring;


import com.thoughtworks.xstream.annotations.XStreamOmitField;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.crud.SimpleGameObjectsCRUD;
import groovy.api.GroovyFactory;
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
        entityDB = new SimpleGameObjectsCRUD(gridWidth, gridHeight);

        factory = new GroovyFactory(entityDB);

        phaseDB = new PhaseDB(factory);
    }

    public void setGridDimension(int width, int height) {
        entityDB.setDimension(width, height);
    }

    public GroovyFactory factory() { return factory; }
    public GameObjectsCRUDInterface entityDB() { return entityDB; }
    public PhaseDB phaseDB() { return phaseDB; }

    public String toEngineXML() { return Serializers.forEngine().toXML(this); }
    public String toAuthoringXML() { return null; }
}
