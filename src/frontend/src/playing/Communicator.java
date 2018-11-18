package playing;

import javax.script.ScriptEngine;

public class Communicator implements Communicable{
    ScriptEngine myEngine;
    DisplayData myDisplayData;

    public Communicator(ScriptEngine engine, DisplayData displayData){
        myEngine = engine;
        myDisplayData = displayData;

    }

    @Override
    public void addNewEntity(int id) {
        //EntityView newEntity = new EntityView()
    }
}
