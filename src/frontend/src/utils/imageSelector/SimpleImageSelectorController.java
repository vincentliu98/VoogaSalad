package utils.imageSelector;

import gameObjects.entity.EntityClass;
import gameObjects.tile.TileClass;
import graphUI.groovy.GroovyPaneFactory;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import groovy.api.GroovyFactory;

import java.util.HashMap;

public class SimpleImageSelectorController implements ImageSelectorController {
    private HashMap<Object, GroovyPane> cachedPanes;
    private GroovyFactory graphFactory;
    private GroovyPaneFactory paneFactory;

    public SimpleImageSelectorController(GroovyFactory graphFactory, GroovyPaneFactory paneFactory) {
        this.graphFactory = graphFactory;
        this.paneFactory = paneFactory;
        cachedPanes = new HashMap<>();
    }

    @Override
    public GroovyPane groovyPaneOf(EntityClass entityClass) {
        System.out.println(entityClass);
        if (entityClass.getImageSelector() == null)
            entityClass.setImageSelector(graphFactory.createDefaultImageSelector());
        if (!cachedPanes.containsKey(entityClass)) {
            var pane = paneFactory.gen(entityClass.getImageSelector(), false);
            cachedPanes.put(entityClass, pane);
        }
        return cachedPanes.get(entityClass);
    }

    @Override
    public GroovyPane groovyPaneOf(TileClass tileClass) {
        if (tileClass.getImageSelector() == null) tileClass.setImageSelector(graphFactory.createDefaultImageSelector());
        if (!cachedPanes.containsKey(tileClass)) {
            var pane = paneFactory.gen(tileClass.getImageSelector(), false);
            cachedPanes.put(tileClass, pane);
        }
        return cachedPanes.get(tileClass);
    }
}
