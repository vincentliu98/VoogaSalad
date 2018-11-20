package gameplay;

public class Tag {

    private Class myType;
    private int myID;

    public Tag(Class type, int id){
        this.myType = type;
        this.myID = id;
    }

    public Class getType(){
        return myType;
    }

    public int getID(){
        return myID;
    }
}
