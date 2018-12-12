package gameObjects.sound;

import java.util.Map;

public class SimpleSoundInstance implements SoundInstance {
    private String className;
    private String instanceName;
    private int instanceId;

    private double duration;
    private String mediaFilePath;
    private Map<String, String> propertiesMap;
    private SoundClass soundClass;

    public SimpleSoundInstance(
            String className,
            String mediaFilePath,
            Map<String, String> properties,
            double duration,
            SoundClass soundClass
    ) {
        this.className = className;
        this.duration = 0;
        this.instanceName = className;
        this.mediaFilePath = mediaFilePath;
        this.propertiesMap = properties;
        this.soundClass = soundClass;
        this.duration = duration;
        instanceId = 0;
    }

    /**
     * @return
     */
    @Override
    public int getInstanceId() {
        return instanceId;
    }

    @Override
    public void setInstanceId(int newId) {
        instanceId = newId;
    }

    /**
     * @return
     */
    @Override
    public String getClassName() {
        return className;
    }

    /**
     * @param name
     */
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


    /**
     * @param propertyName
     * @param defaultValue
     * @return
     */
    @Override
    public void addProperty(String propertyName, String defaultValue) {
        propertiesMap.put(propertyName, defaultValue);
    }

    /**
     * @param propertyName
     * @return
     */
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
    public String getMediaFilePath() {
        return mediaFilePath;
    }

    @Override
    public void setMediaFilePath(String newMediaFilePath) {
        mediaFilePath = newMediaFilePath;
    }

    @Override
    public double getDuration() {
        return duration;
    }

    @Override
    public void setDuration(double newDuration) {
        duration = newDuration;
    }

    @Override
    public SoundClass getGameObjectClass() {
        return soundClass;
    }

}
