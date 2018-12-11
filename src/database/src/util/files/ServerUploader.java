package util.files;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class ServerUploader extends ServerConnector implements ServerUpload {

    public static final String CONNECTION_PROTOCOL = "sftp";

    public ServerUploader() {
        super();
    }

    public void uploadFile(String filePath, String destinationPath) {
        try {
            mySession.connect();
            Channel channel = mySession.openChannel(CONNECTION_PROTOCOL);
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            if (useDefaultPath) {
                sftpChannel.put(filePath, HOST_PATH + destinationPath);
            } else {
                sftpChannel.put(filePath, destinationPath);
            }
            sftpChannel.exit();
            mySession.disconnect();
        } catch (JSchException e) {
            System.out.println("Error");
        } catch (SftpException e) {
            System.out.println("Error");
        }
    }

//    public static void main(String args[]){
//        ServerUploader upload = new ServerUploader();
//        upload.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
//        upload.uploadFile("/Users/jonathannakagawa/Desktop/Stuff/CompSci308/voogasalad_printstacktrace/src/database/resources/blah.txt", "/home/vcm/public_html" );
//    }

}