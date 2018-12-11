package utils.serializer;

import authoringUtils.exception.DuplicateGameObjectClassException;
import gameObjects.category.CategoryClass;
import gameObjects.crud.GameObjectsCRUDInterface;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import org.xml.sax.SAXException;
import utils.ErrorWindow;
import utils.exception.XMLParsingException;
import utils.nodeInstance.NodeInstanceController;

import java.io.File;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * This class handles the reconstruction of CRUD from XML files.
 *
 * @author Haotian Wang
 */
public class CRUDReconstructor {
    private GameObjectsCRUDInterface crudManager;
    private TreeView<String> sideView;
    private GridPane gridPane;
    private NodeInstanceController nodeInstanceController;
    private XMLParser xmlParser;
    private BiConsumer<Integer, Integer> updateDimension;

    public CRUDReconstructor(GameObjectsCRUDInterface crudInterface, TreeView<String> treeView, GridPane grid, NodeInstanceController controller, BiConsumer<Integer, Integer> biConsumer) {
        crudManager = crudInterface;
        sideView = treeView;
        gridPane = grid;
        nodeInstanceController = controller;
        xmlParser = new XMLParser();
        updateDimension = biConsumer;
    }

    /**
     * This method reconstructs everything that contains all the listeners and everything to when the game project is saved.
     *
     * @param xmlFile: A String filename of the XML file.
     * @throws XMLParsingException
     * @throws SAXException
     * @throws CRUDLoadException
     */
    public void reconstruct(String xmlFile) throws XMLParsingException, SAXException, CRUDLoadException {
        xmlParser.loadXML(new File(Objects.requireNonNull(CRUDReconstructor.class.getClassLoader().getResource(xmlFile)).getPath()));
        crudManager.setDimension(xmlParser.getGridWidth(), xmlParser.getGridHeight());
        updateDimension.accept(xmlParser.getGridWidth(), xmlParser.getGridHeight());
        xmlParser.getGameObjectClasses().forEach(rawClass -> {
            if (rawClass.getType().equals("categoryClass")) {
                try {
                    CategoryClass categoryClass = crudManager.createCategoryClass(rawClass.getClassName());
                } catch (DuplicateGameObjectClassException e) {
                    new ErrorWindow("Duplicate GameObjectClass", e.toString());
                }
            }
        });
    }
}
