package conversion.engine;

import authoringUtils.exception.GameObjectClassNotFoundException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.collections.TreeSetConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import gameObjects.category.CategoryClass;
import gameObjects.crud.SimpleGameObjectsCRUD;
import gameObjects.entity.EntityClass;
import gameObjects.tile.TileClass;
import groovy.lang.GroovyShell;

import java.util.Map;
import java.util.TreeSet;

@SuppressWarnings("Duplicates")
public class GameObjectsCRUDConverter implements Converter {
    private Mapper mapper;

    public GameObjectsCRUDConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext ctx) {
        var db = (SimpleGameObjectsCRUD) o;
        var shell = new GroovyShell();

        writer.startNode("grid-width");
        writer.setValue(String.valueOf(db.getWidth()));
        writer.endNode();
        writer.startNode("grid-height");
        writer.setValue(String.valueOf(db.getHeight()));
        writer.endNode();

        writer.startNode("bgmPath");
        writer.setValue(db.getBGMpath());
        writer.endNode();

        // CategoryPrototypes
        for (CategoryClass categoryClass : db.getCategoryClasses()) {
            writer.startNode("gamePlay.CategoryPrototype");

            // name
            writer.startNode("name");
            writer.setValue(categoryClass.getClassName());
            writer.endNode();

            // id
            writer.startNode("myID");
            writer.setValue(String.valueOf(categoryClass.getClassId()));
            writer.endNode();

            writer.endNode();
        }

        // EntityPrototypes
        for (var entityClass : db.getEntityClasses()) {
            writer.startNode("gameplay.EntityPrototype");

            // name
            writer.startNode("name");
            writer.setValue(entityClass.getClassName());
            writer.endNode();

            // props
            var toEval = mapToString(entityClass.getPropertiesMap());
            writer.startNode("props");
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            // myWidth
            writer.startNode("myWidth");
            writer.setValue(String.valueOf(entityClass.getWidth()));
            writer.endNode();

            // myHeight
            writer.startNode("myHeight");
            writer.setValue(String.valueOf(entityClass.getHeight()));
            writer.endNode();


            // imagePaths
            writer.startNode("myImagePaths");
            entityClass.getImagePathList().forEach(path -> {
                writer.startNode("string");
                writer.setValue(path);
                writer.endNode();
            });
            writer.endNode();

            // imageSelector
            writer.startNode("myImageSelector");
            writer.setValue(entityClass.getImageSelector().transformToGroovy().get(""));
            writer.endNode();

            writer.endNode();
        }

        // EntityInstance
        for (var entityInstance : db.getEntityInstances()) {
            EntityClass entityClass = null;
            try {
                entityClass = db.getEntityClass(entityInstance.getClassName());
            } catch (GameObjectClassNotFoundException ignored) {
            }

            writer.startNode("gameplay.Entity");

            // id
            writer.startNode("myID");
            writer.setValue(String.valueOf(entityInstance.getInstanceId()));
            writer.endNode();

            // name
            writer.startNode("name");
            writer.setValue(entityInstance.getClassName());
            writer.endNode();

            // instance name
            writer.startNode("instanceName");
            writer.setValue(entityInstance.getInstanceName());
            writer.endNode();

            // props
            var toEval = mapToString(entityInstance.getPropertiesMap());
            writer.startNode("props");
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            // myWidth
            writer.startNode("myWidth");
            writer.setValue(String.valueOf(entityClass.getWidth()));
            writer.endNode();

            // myHeight
            writer.startNode("myHeight");
            writer.setValue(String.valueOf(entityClass.getHeight()));
            writer.endNode();

            writer.startNode("myCoord");
            writer.startNode("x");
            writer.setValue(String.valueOf(entityInstance.getCoord().getX()));
            writer.endNode();
            writer.startNode("y");
            writer.setValue(String.valueOf(entityInstance.getCoord().getY()));
            writer.endNode();
            writer.endNode();

            // imagePaths
            writer.startNode("myImagePaths");
            entityClass.getImagePathList().forEach(path -> {
                writer.startNode("string");
                writer.setValue(path);
                writer.endNode();
            });
            writer.endNode();

            // imageSelector
            writer.startNode("myImageSelector");
            writer.setValue(entityClass.getImageSelector().transformToGroovy().get(""));
            writer.endNode();

            writer.endNode();
        }

        // TilePrototype
        for (TileClass tileClass : db.getTileClasses()) {
            writer.startNode("gameplay.TilePrototype");
            // ID
            writer.startNode("myID");
            writer.setValue(String.valueOf(tileClass.getClassId()));
            writer.endNode();

            // name
            writer.startNode("name");
            writer.setValue(tileClass.getClassName());
            writer.endNode();

            // props
            writer.startNode("props");
            var toEval = mapToString(tileClass.getPropertiesMap());
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            // myWidth
            writer.startNode("myWidth");
            writer.setValue(String.valueOf(tileClass.getWidth()));
            writer.endNode();

            // myHeight
            writer.startNode("myHeight");
            writer.setValue(String.valueOf(tileClass.getHeight()));
            writer.endNode();

            //myImagePaths
            writer.startNode("myImagePaths");
            tileClass.getImagePathList().forEach(path -> {
                writer.startNode("string");
                writer.setValue(path);
                writer.endNode();
            });
            writer.endNode();

            // myImageSelector
            writer.startNode("myImageSelector");
            writer.setValue(tileClass.getImageSelector().transformToGroovy().get(""));
            writer.endNode();
            writer.endNode();
        }

        // TileInstance
        for (var tileInstance : db.getTileInstances()) {
            writer.startNode("gameplay.Tile");

            // myID
            writer.startNode("myID");
            writer.setValue(String.valueOf(tileInstance.getInstanceId()));
            writer.endNode();

            // name
            writer.startNode("name");
            writer.setValue(tileInstance.getClassName());
            writer.endNode();

            // instance name
            writer.startNode("instanceName");
            writer.setValue(tileInstance.getInstanceName());
            writer.endNode();

            // props
            var toEval = mapToString(tileInstance.getPropertiesMap()); // should be instance tho...
            writer.startNode("props");
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            // myWidth
            writer.startNode("myWidth");
            writer.setValue(String.valueOf(tileInstance.getWidth()));
            writer.endNode();

            // myHeight
            writer.startNode("myHeight");
            writer.setValue(String.valueOf(tileInstance.getHeight()));
            writer.endNode();

            writer.startNode("myCoord");
            writer.startNode("x");
            writer.setValue(String.valueOf(tileInstance.getCoord().getX()));
            writer.endNode();
            writer.startNode("y");
            writer.setValue(String.valueOf(tileInstance.getCoord().getY()));
            writer.endNode();
            writer.endNode();

            //myImagePaths
            writer.startNode("myImagePaths");
            tileInstance.getImagePathList().forEach(path -> {
                writer.startNode("string");
                writer.setValue(path);
                writer.endNode();
            });
            writer.endNode();

            // myImageSelector
            writer.startNode("myImageSelector");
            writer.setValue(tileInstance.getGameObjectClass().getImageSelector().transformToGroovy().get(""));
            writer.endNode();

            writer.endNode();
        }

        for (var player : db.getPlayerClasses()) {
            writer.startNode("gameplay.Player");

            writer.startNode("myName");
            writer.setValue(player.getClassName());
            writer.endNode();

            writer.startNode("myStats");
            var toEval = mapToString(player.getPropertiesMap());
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            writer.startNode("myEntityIDs");
            new TreeSetConverter(mapper).marshal(new TreeSet<>(player.getAllGameObjectInstanceIDs()), writer, ctx);
            writer.endNode();

            writer.endNode();
        }
    }

    private <K, V> String mapToString(Map<K, V> map) {
        String toEval = "[";
        boolean first = true;
        for (var entry : map.entrySet()) {
            if (!first) toEval += ", ";
            else first = false;
            toEval += String.format("%s:%s", entry.getKey(), entry.getValue());
        }
        if (first) toEval += ":";
        toEval += "]";
        return toEval;
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(SimpleGameObjectsCRUD.class);
    }
}
