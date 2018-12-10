package conversion.authoring;

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
import gameObjects.sound.SoundClass;
import gameObjects.sound.SoundInstance;
import gameObjects.tile.TileClass;
import groovy.lang.GroovyShell;

import java.util.Map;
import java.util.TreeSet;

/**
 * This Converter is for the authoring GameObjectsCRUD saving and loading.
 *
 * @author Haotian Wang
 */
@SuppressWarnings("Duplicates")
public class CRUDConverterAuthoring implements Converter {
    private Mapper mapper;

    public CRUDConverterAuthoring(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext ctx) {
        var db = (SimpleGameObjectsCRUD) o;
        var shell = new GroovyShell();

        writer.startNode("gridWidth");
        writer.setValue(String.valueOf(db.getWidth()));
        writer.endNode();
        writer.startNode("gridHeight");
        writer.setValue(String.valueOf(db.getHeight()));
        writer.endNode();

        // CategoryPrototypes
        for (CategoryClass categoryClass : db.getCategoryClasses()) {
            writer.startNode("categoryClass");

            // name
            writer.startNode("className");
            writer.setValue(categoryClass.getClassName());
            writer.endNode();

            // id
            writer.startNode("classID");
            writer.setValue(String.valueOf(categoryClass.getClassId()));
            writer.endNode();

            writer.endNode();
        }

        // EntityPrototypes
        for(var entityClass : db.getEntityClasses()) {
            writer.startNode("entityClass");

            // name
            writer.startNode("className");
            writer.setValue(entityClass.getClassName());
            writer.endNode();

            // className
            writer.startNode("classID");
            writer.setValue(String.valueOf(entityClass.getClassId()));
            writer.endNode();

            // props
            var toEval = mapToString(entityClass.getPropertiesMap());
            writer.startNode("props");
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            // myWidth
            writer.startNode("width");
            writer.setValue(String.valueOf(entityClass.getWidth()));
            writer.endNode();

            // myHeight
            writer.startNode("height");
            writer.setValue(String.valueOf(entityClass.getHeight()));
            writer.endNode();


            // imagePaths
            writer.startNode("imagePaths");
            entityClass.getImagePathList().forEach(path -> {
                writer.startNode("path");
                writer.setValue(path);
                writer.endNode();
            });
            writer.endNode();

            // imageSelector
            writer.startNode("imageSelector");
            if (entityClass.getImageSelectorCode() != null && !entityClass.getImageSelectorCode().isEmpty()) {
                writer.setValue(entityClass.getImageSelectorCode());
            }
            writer.endNode();

            writer.endNode();
        }

        // EntityInstance
        for(var entityInstance : db.getEntityInstances()) {
            writer.startNode("entityInstance");

            // id
            writer.startNode("instanceID");
            writer.setValue(String.valueOf(entityInstance.getInstanceId()));
            writer.endNode();

            // name
            writer.startNode("className");
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
            writer.startNode("width");
            writer.setValue(String.valueOf(entityInstance.getWidth()));
            writer.endNode();

            // myHeight
            writer.startNode("height");
            writer.setValue(String.valueOf(entityInstance.getHeight()));
            writer.endNode();

            writer.startNode("coord");
            writer.startNode("x");
            writer.setValue(String.valueOf(entityInstance.getCoord().getX()));
            writer.endNode();
            writer.startNode("y");
            writer.setValue(String.valueOf(entityInstance.getCoord().getY()));
            writer.endNode();
            writer.endNode();

            // imagePaths
            writer.startNode("imagePaths");
            entityInstance.getImagePathList().forEach(path -> {
                writer.startNode("path");
                writer.setValue(path);
                writer.endNode();
            });
            writer.endNode();

            // imageSelector
            writer.startNode("imageSelector");
            if (entityInstance.getImageSelectorCode() != null && !entityInstance.getImageSelectorCode().isEmpty()) {
                writer.setValue(entityInstance.getImageSelectorCode());
            }
            writer.endNode();

            writer.endNode();
        }

        // TilePrototype
        for (TileClass tileClass: db.getTileClasses()) {
            writer.startNode("tileClass");
            // ID
            writer.startNode("classID");
            writer.setValue(String.valueOf(tileClass.getClassId()));
            writer.endNode();

            // name
            writer.startNode("className");
            writer.setValue(tileClass.getClassName());
            writer.endNode();

            // props
            writer.startNode("props");
            var toEval = mapToString(tileClass.getPropertiesMap());
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            // myWidth
            writer.startNode("width");
            writer.setValue(String.valueOf(tileClass.getWidth()));
            writer.endNode();

            // myHeight
            writer.startNode("height");
            writer.setValue(String.valueOf(tileClass.getHeight()));
            writer.endNode();

            //myImagePaths
            writer.startNode("imagePaths");
            tileClass.getImagePathList().forEach(path -> {
                writer.startNode("path");
                writer.setValue(path);
                writer.endNode();
            });
            writer.endNode();

            // myImageSelector
            writer.startNode("imageSelector");
            if (tileClass.getImageSelectorCode() != null && !tileClass.getImageSelectorCode().isEmpty()) {
                writer.setValue(tileClass.getImageSelectorCode());
            }
            writer.endNode();
            writer.endNode();
        }

        // TileInstance
        for(var tileInstance : db.getTileInstances()) {
            writer.startNode("tileInstance");

            // myID
            writer.startNode("instanceID");
            writer.setValue(String.valueOf(tileInstance.getInstanceId()));
            writer.endNode();

            // name
            writer.startNode("className");
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
            writer.startNode("width");
            writer.setValue(String.valueOf(tileInstance.getWidth()));
            writer.endNode();

            // myHeight
            writer.startNode("height");
            writer.setValue(String.valueOf(tileInstance.getHeight()));
            writer.endNode();

            writer.startNode("coord");
            writer.startNode("x");
            writer.setValue(String.valueOf(tileInstance.getCoord().getX()));
            writer.endNode();
            writer.startNode("y");
            writer.setValue(String.valueOf(tileInstance.getCoord().getY()));
            writer.endNode();
            writer.endNode();

            //myImagePaths
            writer.startNode("imagePaths");
            tileInstance.getImagePathList().forEach(path -> {
                writer.startNode("path");
                writer.setValue(path);
                writer.endNode();
            });
            writer.endNode();

            // myImageSelector
            writer.startNode("imageSelector");
            if (tileInstance.getImageSelectorCode() != null && !tileInstance.getImageSelectorCode().isEmpty()) {
                writer.setValue(tileInstance.getImageSelectorCode());
            }
            writer.endNode();

            writer.endNode();
        }

        for(var player : db.getPlayerClasses()) {
            writer.startNode("playerClass");

            writer.startNode("className");
            writer.setValue(player.getClassName());
            writer.endNode();

            writer.startNode("classID");
            writer.setValue(String.valueOf(player.getClassId()));
            writer.endNode();

            writer.startNode("props");
            var toEval = mapToString(player.getPropertiesMap());
            new MapConverter(mapper).marshal(shell.evaluate(toEval), writer, ctx);
            writer.endNode();

            writer.startNode("gameObjectInstanceIDs");
            new TreeSetConverter(mapper).marshal(new TreeSet<>(player.getAllGameObjectInstanceIDs()), writer, ctx);
            writer.endNode();

            writer.startNode("imagePath");
            if (!player.getImagePath().isEmpty()) {
                writer.setValue(player.getImagePath());
            }
            writer.endNode();

            writer.endNode();
        }

        // SoundClass
        for (SoundClass soundClass : db.getSoundClasses()) {
            writer.startNode("soundClass");

            // Class Name
            writer.startNode("className");
            writer.setValue(soundClass.getClassName());
            writer.endNode();

            writer.startNode("classID");
            writer.setValue(String.valueOf(soundClass.getClassId()));
            writer.endNode();

            writer.startNode("mediaFilePath");
            if (!soundClass.getMediaFilePath().isEmpty()) {
                writer.setValue(soundClass.getMediaFilePath());
            }
            writer.endNode();

            writer.startNode("duration");
            writer.setValue(String.valueOf(soundClass.getDuration()));
            writer.endNode();

            writer.endNode();
        }

        // SoundInstance
        for (SoundInstance soundInstance : db.getSoundInstances()) {
            writer.startNode("soundInstance");

            // ClassName
            writer.startNode("className");
            writer.setValue(soundInstance.getClassName());
            writer.endNode();

            // Instance name
            writer.startNode("instanceName");
            writer.setValue(soundInstance.getInstanceName());
            writer.endNode();

            // Instance ID
            writer.startNode("instanceID");
            writer.setValue(String.valueOf(soundInstance.getInstanceId()));
            writer.endNode();

            // duration
            writer.startNode("duration");
            writer.setValue(String.valueOf(soundInstance.getDuration()));
            writer.endNode();

            // file path
            writer.startNode("mediaFilePath");
            if (!soundInstance.getMediaFilePath().isEmpty()) {
                writer.setValue(soundInstance.getMediaFilePath());
            }
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
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(SimpleGameObjectsCRUD.class);
    }
}
