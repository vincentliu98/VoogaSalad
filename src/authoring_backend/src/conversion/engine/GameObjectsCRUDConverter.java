package conversion.engine;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import gameObjects.crud.SimpleGameObjectsCRUD;
import groovy.lang.GroovyShell;

import java.util.Map;

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

        // EntityPrototypes
        for(var entityClass : db.getEntityClasses()) {
            writer.startNode("gameplay.EntityPrototype");

            // name
            writer.startNode("name");
            writer.setValue(entityClass.getClassName().get());
            writer.endNode();

            // props
            var toEval = mapToString(entityClass.getPropertiesMap());
            writer.startNode("props");
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
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
            writer.setValue(entityClass.getImageSelectorCode());
            writer.endNode();

            writer.endNode();
        }

        for(var entityInstance : db.getEntityInstances()) {
            var entityClass = db.getEntityClass(entityInstance.getClassName().get());

            writer.startNode("gameplay.Entity");

            // id
            writer.startNode("myID");
            writer.setValue(String.valueOf(entityInstance.getInstanceId().get()));
            writer.endNode();

            // name
            writer.startNode("name");
            writer.setValue(entityInstance.getClassName().get());
            writer.endNode();

            // tileID
            writer.startNode("tileID");
            writer.setValue(String.valueOf(entityInstance.getTileID().get()));
            writer.endNode();

            // props
            var toEval = mapToString(entityClass.getPropertiesMap());
            writer.startNode("props");
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
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
            writer.setValue(entityClass.getImageSelectorCode());
            writer.endNode();

            writer.endNode();
        }

        for(var tileInstance : db.getTileInstances()) {
            var tileClass = db.getTileClass(tileInstance.getClassName().get());
            writer.startNode("gameplay.Tile");

            // myID
            writer.startNode("myID");
            writer.setValue(String.valueOf(tileInstance.getInstanceId().get()));
            writer.endNode();

            // name
            writer.startNode("name");
            writer.setValue(tileClass.getClassName().get());
            writer.endNode();

            // props
            var toEval = mapToString(tileClass.getPropertiesMap()); // should be instance tho...
            writer.startNode("props");
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            // myWidth
            writer.startNode("myWidth");
            writer.setValue(String.valueOf(tileClass.getWidth().get()));
            writer.endNode();

            // myHeight
            writer.startNode("myHeight");
            writer.setValue(String.valueOf(tileClass.getHeight().get()));
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
            for(var path : tileClass.getImagePathList()) {
                writer.startNode("string");
                writer.setValue(path);
                writer.endNode();
            }
            writer.endNode();

            // myImageSelector
            writer.startNode("myImageSelector");
            writer.setValue(tileClass.getImageSelectorCode());
            writer.endNode();

            writer.endNode();
        }
    }

    private <K, V> String mapToString(Map<K, V> map) {
        String toEval = "[";
        boolean first = true;
        for(var entry: map.entrySet()) {
            if(!first) toEval += ", ";
            else first = false;
            toEval += String.format("%s:%s", entry.getKey(), entry.getValue());
        }
        if(first) toEval += ":";
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
