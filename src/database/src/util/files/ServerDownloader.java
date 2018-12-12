package util.files;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Vector;

public class ServerDownloader extends ServerConnector implements ServerQuery {

    public static final String CONNECTION_PROTOCOL = "sftp";
    public static final String BLACKLIST_PATH = "properties/BlackListForDownload";

    private ResourceBundle myResource;
    private HashSet myBlackList;

    public ServerDownloader() {
        super();

        myResource = ResourceBundle.getBundle(BLACKLIST_PATH);
        createMap();
    }

    public static void main(String args[]) {
        ServerDownloader downloader = new ServerDownloader();
        downloader.connectServer("vcm", "vcm-7456.vm.duke.edu", 22, "afcas8amYf");
        //downloader.downloadFile("/tester.txt","/Users/jonathannakagawa/Desktop/Stuff/CompSci308/voogasalad_printstacktrace/src/database/resources");
        downloader.dowloadDirectory("/games", "/Users/jonathannakagawa/Desktop/Stuff/CompSci308/voogasalad_printstacktrace/src/database/resources", "games");
    }

    @Override
    public void downloadFile(String filePath, String fileDestination) {
        System.out.println(filePath);
        System.out.println(fileDestination);
        try {
            mySession.connect();
            Channel channel = mySession.openChannel(CONNECTION_PROTOCOL);
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(filePath, fileDestination);
            sftpChannel.exit();
            mySession.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
            System.out.println("An error is happening here");
        }
    }

    @Override
    public void dowloadDirectory(String directoryPath, String directoryDestination, String directoryName) {
        try {
            mySession.connect();
            Channel channel = mySession.openChannel(CONNECTION_PROTOCOL);
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            String srcPath = directoryPath;

            if (useDefaultPath) {
                srcPath = HOST_PATH + directoryPath;
            }

            Vector<ChannelSftp.LsEntry> files = sftpChannel.ls(srcPath);

            File dir = new File(directoryDestination + "/" + directoryName);
            dir.mkdir();


            System.out.println(files.size());
            for (ChannelSftp.LsEntry entry : files) {
                System.out.println(entry.getFilename());
                if (!validDowload(entry.getFilename())) {
                    continue;
                }
                sftpChannel.get(srcPath + '/' + entry.getFilename(), directoryDestination + "/" + directoryName);
            }

            sftpChannel.exit();
            mySession.disconnect();

        } catch (JSchException e) {
            System.out.println("Error");
        } catch (SftpException e) {
            System.out.println("Error");
            e.printStackTrace();

        }
    }

//    public static void main(String args[]){
//        ServerDownloader downloader = new ServerDownloader();
//        downloader.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
//        downloader.downloadFile("/home/vcm/public_html/users/profiles/JonathanNakagawa.xml","/Users/Natalie" +
//                "/CompSci308" +
//                "/Projects/voogasalad_printstacktrace/src/database/resources");
//    }

    private boolean validDowload(String name) {
        return !myBlackList.contains(name);
    }

    private void createMap() {
        myBlackList = new HashSet();

        for (var key : Collections.list(myResource.getKeys())) {
            String[] vals = myResource.getString(key).split("\\|");
            for (String val : vals) {
                myBlackList.add(val);
            }
        }
    }
}