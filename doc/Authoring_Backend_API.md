# GameObjectClass
When we call setClassName on GameObjectClass, it should change the class name for all the associated instances as well.
```java
String getClassName()
int getID()
void setClassName(String name)
GameObjectType getType()
ObservableList<GameObjectInstance> getAllInstances()
```
I don't think this super class should have methods that get images etc.

### EntityClass
```java
ObservableList<String> getImagePathList()
void addImagePath(String filePath)
```

### SoundClass
```java
String getMediaFilePath()
void setMediaFilePath(String filePath)
```

### CategoryClass
Maybe I want an icon for a category.
```java
String getImagePath()
void setImagePath(String filePath)
```

### TileClass
```java
ObservableList<String> getImagePathList()
void addImagePath(String filePath)
```

# GameObjectInstance
```java
int getID()
String getClassName()
String getInstanceName()
void setInstanceName()
GameObjectType getType()
setPoint(int x, int y)
setPoint(Point position)
Point getPoint()
ObservableList<GameObjectInstance> getInstancesAtSamePoint(GameObjectInstance instance)
```
GameObjectInstance should not be able to change GameObjectClass name, but if you like it , you can do that as well. If we call changeGameObjectClassName on this instance, it should change the class names of all the brother instances and the class for that instance.

### EntityInstance
```java
ObservableList<String> getImagePathList()
void addImagePath(String filePath)
```

### SoundInstance
```java
String getMediaFilePath()
void setMediaFilePath(String filePath)
```

### CategoryInstance
```java
ObservableList<String> getImagePathList()
void addImagePath(String filePath)
```

### TileInstance
```java
ObservableList<String> getImagePathList()
void addImagePath(String filePath)
```

# GameXXXCRUDXXSOMETHING
```java
GameObjectClass getGameObjectClass(String className)
GameObjectInstance getGameObjectInstance(int id)
ObservableList<GameObjectInstance> getAllInstances(String className)
ObservableList<GameObjectInstance> getAllInstances(GameObjectClass gameObjectClass)
ObservableList<GameObjectInstance> getAllInstancesAtPoint(int x, int y)
ObservableList<GameObjectInstance> getAllInstancesAtPoint(Point point)
```
Now your methods return void. Just return the newly created instance, so I can modify them directly instead of I having to call another get method to get the newly created instance. Also another method that creates instances based on GameObjectClass in addition to class names would be nice.
```java
EntityInstance createEntityInstance(String className)
EntityInstance createEntityInstance(EntityClass entityClass)
SoundInstance createSoundInstance(String className)
SoundInstance createSoundInstance(SoundClass soundClass)
CategoryInstance createCategoryInstance(String className)
CategoryInstance createCategoryInstance(CategoryClass categoryClass)
TileInstance createTileInstance(String className)
TileInstance createTileInstance(TileClass tileClass)
```
This class has a name changing method as well.
```java
void changeClassName(String oldClassName, String newClassName)
```
Different removal methods
```java
void removeGameObjectClass(String className)
void removeGameObjectClass(GameObjectClass gameObjectClass)
```