package util.files;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ServerConnector {

//    mySession = myJsch.getSession("vcm", "vcm-7456.vm.duke.edu", 22);
//            mySession.setConfig("StrictHostKeyChecking", "no");
//            mySession.setPassword("afcas8amYf");
//            mySession.connect();


    protected JSch myJsch;
    protected Session mySession;

    public ServerConnector(){
        myJsch = new JSch();
        mySession = null;
    }

    public void connectServer(String username, String host, int port, String password){
        try{
            mySession = myJsch.getSession(username, host, port);
            mySession.setConfig("StrictHostKeyChecking", "no");
            mySession.setPassword(password);
        }
        catch (JSchException e){
            System.out.println("Failed to Connect");
        }

    }
}
