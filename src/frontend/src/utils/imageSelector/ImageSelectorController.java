package utils.imageSelector;

import gameObjects.entity.EntityClass;
import gameObjects.tile.TileClass;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;

public interface ImageSelectorController {
    GroovyPane groovyPaneOf(EntityClass entityClass);

    GroovyPane groovyPaneOf(TileClass tileClass);
}
