package util.files;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ServerConnector {
    public static final String HOST_PATH = "/home/vcm/public_html";

    protected boolean useDefaultPath;

//    mySession = myJsch.getSession("vcm", "vcm-7456.vm.duke.edu", 22);
//            mySession.setConfig("StrictHostKeyChecking", "no");
//            mySession.setPassword("afcas8amYf");
//            mySession.connect();


    protected JSch myJsch;
    protected Session mySession;

    public ServerConnector() {
        myJsch = new JSch();
        mySession = null;
        useDefaultPath = true;
    }

    public void connectServer(String username, String host, int port, String password) {
        try {
            mySession = myJsch.getSession(username, host, port);
            mySession.setConfig("StrictHostKeyChecking", "no");
            mySession.setPassword(password);
        } catch (JSchException e) {
            System.out.println("Failed to Connect");
        }

    }

    public void useFullPath() {
        useDefaultPath = false;
    }

    public void usePresetPath() {
        useDefaultPath = true;
    }
}
