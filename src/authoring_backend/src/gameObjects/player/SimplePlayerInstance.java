package gameObjects.player;

import java.util.Map;

/**
 * @author Yunhao Qing
 */


public class SimplePlayerInstance implements PlayerInstance {
    private String className;
    private String instanceName;
    private int instanceId;

    private String imagePath;
    private Map<String, String> propertiesMap;
    private PlayerClass playerClass;


    public SimplePlayerInstance(String className,
                                String imagePath,
                                Map<String, String> properties,
                                PlayerClass playerClass) {
        this.className = className;
        this.instanceName = className;
        this.imagePath = imagePath;
        this.propertiesMap = properties;
        this.playerClass = playerClass;
        instanceId = 0;
    }

    @Override
    public int getInstanceId() {
        return instanceId;
    }

    @Override
    public void setInstanceId(int newId) {
        instanceId = newId;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String name) {
        className = name;
    }

    @Override
    public String getInstanceName() {
        return instanceName;
    }

    @Override
    public void setInstanceName(String newInstanceName) {
        instanceName = newInstanceName;
    }

    @Override
    public Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }


    @Override
    public void addProperty(String propertyName, String defaultValue) {
        propertiesMap.put(propertyName, defaultValue);
    }

    @Override
    public void removeProperty(String propertyName) {
        propertiesMap.remove(propertyName);
    }

    @Override
    public boolean changePropertyValue(String propertyName, String newValue) {
        if (!propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.put(propertyName, newValue);
        return true;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String newImagePath) {
        imagePath = newImagePath;
    }

    @Override
    public PlayerClass getGameObjectClass() {
        return playerClass;
    }

}
