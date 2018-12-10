package util.files;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class ServerDownloader extends ServerConnector implements ServerQuery{

    public static final String CONNECTION_PROTOCOL = "sftp";

    //sftpChannel.get("/home/vcm/public_html/tester.txt", "/Users/jonathannakagawa/Desktop/Stuff/CompSci308/voogasalad_printstacktrace/src/database/resources");

    public ServerDownloader(){
        super();
        connectServer("vcm", "vcm-7456.vm.duke.edu", 22, "afcas8amYf");
    }

    @Override
    public void downloadFile(String filePath, String fileDestination){
        try{
            mySession.connect();
            Channel channel = mySession.openChannel(CONNECTION_PROTOCOL);
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            if(useDefaultPath){
                sftpChannel.get(filePath, HOST_PATH + fileDestination);
            }
            else {
                sftpChannel.get(filePath, fileDestination);
            }
            sftpChannel.exit();
            mySession.disconnect();
        }
        catch (JSchException e) {
            System.out.println("Error");
        }
        catch (SftpException e) {
            System.out.println("Error");
        }
    }

//    public static void main(String args[]){
//        ServerDownloader downloader = new ServerDownloader();
//        downloader.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
//        downloader.downloadFile("/home/vcm/public_html/tester.txt","/Users/jonathannakagawa/Desktop/Stuff/CompSci308/voogasalad_printstacktrace/src/database/resources");
//    }

}