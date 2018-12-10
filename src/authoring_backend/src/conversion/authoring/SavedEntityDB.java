package conversion.authoring;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;

import java.util.HashSet;

public class SavedEntityDB {
    private int numRows;
    private int numCols;
    private HashSet<GameObjectClass> classes;
    private HashSet<GameObjectInstance> instances;

    SavedEntityDB(
        int numRows,
        int numCols,
        HashSet<GameObjectClass> classes,
        HashSet<GameObjectInstance> instances
    ) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.classes = classes;
        this.instances = instances;
    }

    public int numRows() { return numRows; }
    public int numCols() { return numCols; }
    public HashSet<GameObjectClass> classes() { return classes; }
    public HashSet<GameObjectInstance> instances() { return instances; }
}
