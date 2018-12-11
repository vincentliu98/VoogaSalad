package gameplay;

public class ClickTag {
    private Class myType;
    private int myID;

    public ClickTag(Class type, int id) {
        this.myType = type;
        this.myID = id;
    }

    public Class getType() {
        return myType;
    }

    public int getID() {
        return myID;
    }
}
