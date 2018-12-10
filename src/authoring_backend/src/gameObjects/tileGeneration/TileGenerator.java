package gameObjects.tileGeneration;

import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidOperationException;
import authoringUtils.exception.NumericalException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.tile.TileClass;
import grids.Point;
import grids.PointImpl;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class TileGenerator {
    private TileClass[] tileClasses;
    private ArrayList<Double> cdf;
    private GameObjectsCRUDInterface crudInterface;
    private GenerationMode mode;
    private int noOfClasses;
    private Point topLeftCoord;
    private int rightLimit;
    private int bottomLimit;
    private Random random;

    public TileGenerator(Map<Double, TileClass> tileClasses, GameObjectsCRUDInterface crudInterface, GenerationMode mode)
            throws GameObjectClassNotFoundException, NumericalException {
        this.crudInterface = crudInterface;
        this.mode = mode;
        this.noOfClasses = 0;
        Double checkInput = 0.0;
        if (tileClasses.isEmpty()) {
            throw new GameObjectClassNotFoundException("Map is empty");
        }
        for (Map.Entry<Double, TileClass> e : tileClasses.entrySet()) {
            checkInput += e.getKey();
            cdf.add(checkInput);
            noOfClasses += 1;
            crudInterface.getTileClass(e.getValue().getClassName().getValue());
        }
        if (mode == GenerationMode.RANDOM && checkInput != 1) {
            throw new NumericalException("Probabilities do not sum to 1");
        }
        this.tileClasses = (TileClass[]) tileClasses.values().toArray();
        rightLimit = 0;
        bottomLimit = 0;
        this.random = new Random();
    }


    /**
     * topLeftCoord inclusive
     * @param topLeftCoord
     * @param numRows
     * @param numCols
     */
    public void getTileGenerationArea(Point topLeftCoord, int numRows, int numCols)
            throws InvalidOperationException {
        this.topLeftCoord = topLeftCoord;
        rightLimit = topLeftCoord.getX() + numCols;
        bottomLimit = topLeftCoord.getY() + numRows;
        if (rightLimit > crudInterface.getNumCols() || bottomLimit > crudInterface.getNumRows()) {
            throw new InvalidOperationException("Area out of bounds");
        }

    }

    public void generateTiles() throws Exception {
        if (topLeftCoord == null || rightLimit == 0 || bottomLimit == 0) {
            throw new Exception("Values uninitialized");
        }
        if (mode == GenerationMode.REPEATING) {
            generateRepeatingTiles();
        }
        else if (mode == GenerationMode.RANDOM) {
            generateRandomTiles();
        }
    }

    private void generateRepeatingTiles() throws GameObjectTypeException {
        int repeater = 0;
        for (int i = topLeftCoord.getY(); i < bottomLimit; i++) {
            for (int j = topLeftCoord.getX(); j < rightLimit; j++) {
                crudInterface.createTileInstance(tileClasses[repeater % noOfClasses], new PointImpl(j, i));
            }
        }
    }

    private void generateRandomTiles() throws GameObjectTypeException {
        for (int i = topLeftCoord.getY(); i < bottomLimit; i++) {
            for (int j = topLeftCoord.getX(); j < rightLimit; j++) {
                Double randomDouble = random.nextDouble();
                int desired = 0;
                for (int k = 0; k < cdf.size(); k++) {
                    if (randomDouble < cdf.get(k)) {
                        desired = k;
                        break;
                    }
                }
                crudInterface.createTileInstance(tileClasses[desired], new PointImpl(j, i));
            }
        }
    }

}
