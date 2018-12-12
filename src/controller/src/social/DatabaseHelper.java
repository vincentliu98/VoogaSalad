package social;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import exceptions.LoginException;
import exceptions.RegistrationException;
import exceptions.UserException;
import util.data.DatabaseDownloader;
import util.data.DatabaseUploader;
import util.files.ServerDownloader;
import util.files.ServerUploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DatabaseHelper {
    private static final ResourceBundle myErrors = ResourceBundle.getBundle("Errors");
    private static final DatabaseUploader DATABASE_UPLOADER = new DatabaseUploader("client",
            "store","e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
    private static final DatabaseDownloader DATABASE_DOWNLOADER = new DatabaseDownloader("client", "store",
            "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
    private static final ServerUploader SERVER_UPLOADER = new ServerUploader();
    private static final ServerDownloader SERVER_DOWNLOADER = new ServerDownloader();

    public static void updateRemoteProfileReferences(String myUsername, int id) {
        DATABASE_UPLOADER.upload(String.format("INSERT INTO userReferences (id, profilePath) " +
                "VALUES ('%d','%s')", id, "/home/vcm/public_html/users/profiles/" + myUsername + ".xml"));
    }

    private static void connectServerUploader(){
        SERVER_UPLOADER.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
    }
    private static void connectServerDownloader(){
        SERVER_DOWNLOADER.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
    }

    public static void uploadSerializedUserFile(String myUsername, User user) throws IOException {
        XStream serializer = new XStream(new DomDriver());
        File userFile=new File("src/database/resources/" + myUsername + ".xml");
        if (!userFile.exists()){
            userFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(userFile);
        fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + serializer.toXML(user));
        fileWriter.close();
        connectServerUploader();
        SERVER_UPLOADER.uploadFile(userFile.getAbsolutePath(), "/users/profiles");
        userFile.delete();
    }

    public static void updateLoginsTable(String myUsername, String myPassword, int id) {
        DATABASE_UPLOADER.upload(String.format("INSERT INTO logins (username, id, password) VALUES ('%s','%d'," +
                "'%s')", myUsername, id, myPassword));
    }

    public static void verifyUniqueUsername(String myUsername) throws SQLException {
        ResultSet result = DATABASE_DOWNLOADER.queryServer("SELECT username, id FROM logins");
        while (result.next()){
            String remoteUsername = result.getString("username");
            if (remoteUsername.equals(myUsername)){
                throw new RegistrationException(myErrors.getString("UsernameAlreadyExists"),
                        myErrors.getString("UsernameAlreadyExistsWarning"));
            }
        }
    }

    public static int getIDByUsernameAndPassword(String username, String password) throws SQLException {
        ResultSet result = DATABASE_DOWNLOADER.queryServer(String.format("SELECT id FROM logins WHERE " +
                "username='%s' AND password='%s'", username, password));
        if (!result.next()){
            throw new LoginException(myErrors.getString("NonexistentAccount"), myErrors.getString(
                    "NonexistentAccountWarning"));
        }
        result.last();
        return result.getInt("id");

    }

    public static int getNextUserID(){
        try {
            ResultSet result = DATABASE_DOWNLOADER.queryServer("SELECT id FROM logins");
            result.beforeFirst();
            if (!result.next()){
                return 1;
            } else {
                result.last();
                return result.getInt("id") + 1;
            }
        } catch (Exception e){
            throw new RegistrationException(myErrors.getString("SQLError"), myErrors.getString("SQLErrorWarning"));
        }

    }

    public static void updateRemoteAvatar(File localAvatarFile, int userID){
        SERVER_UPLOADER.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
        SERVER_UPLOADER.uploadFile(localAvatarFile.getAbsolutePath(), "/users/avatars");
        // update reference
        DATABASE_UPLOADER.upload(String.format("UPDATE userReferences " +
                        "SET avatarPath='%s' WHERE id='%d'", "/home/vcm/public_html/users/avatars/" + localAvatarFile.getName(),
                        userID));
    }

    private static String getProfilePath(int id) throws SQLException {
        ResultSet result = DATABASE_DOWNLOADER.queryServer(String.format("SELECT profilePath FROM" +
                " userReferences WHERE id='%d'", id));
        if (!result.next()) throw new UserException("the account doesn't exist", "oh no"); // TODO: ERase
        result.last();
        return result.getString("profilePath");
    }

    private static String getAvatarPath(int id) throws SQLException {
        ResultSet result = DATABASE_DOWNLOADER.queryServer(String.format("SELECT avatarPath FROM" +
                " userReferences WHERE id='%d'", id));
        result.last();
        System.out.println("getting avatar path " + result.getString("avatarPath"));
        return result.getString("avatarPath");
    }

    private static String downloadRemoteXML(String profilePath) throws SQLException {
        connectServerDownloader();
        File directory = new File("src/database/resources/");
        SERVER_DOWNLOADER.downloadFile(profilePath,directory.getAbsolutePath());
        String[] filePathArray = profilePath.split("/");
        return filePathArray[filePathArray.length - 1];
    }

    private static String downloadRemoteAvatar(String avatarPath) throws SQLException {
        connectServerDownloader();
        File directory = new File("src/controller/resources/profile-images/");
        SERVER_DOWNLOADER.downloadFile(avatarPath, directory.getAbsolutePath());
        String[] filePathArray = avatarPath.split("/");
        return filePathArray[filePathArray.length - 1];
    }

    private static User deserializeUser(String fileName) throws FileNotFoundException {
        try {
            XStream serializer = new XStream(new DomDriver());
            System.out.println("deserializing-fiilename is " + fileName);
            File file = new File("src/database/resources/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner scanner = new Scanner(file, "UTF-8");
            String text = scanner.useDelimiter("\\A").next();
            scanner.close();
            text = text.trim().replaceFirst("^([\\W]+)<", "<");
            file.delete();
            return (User) serializer.fromXML(text);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static int getIDByUsername(String username){
        try{
            ResultSet result = DATABASE_DOWNLOADER.queryServer(String.format("SELECT id FROM logins WHERE " +
                    "username='%s'", username));
            result.last();
            return result.getInt("id");
        } catch (Exception e){
            throw new UserException(myErrors.getString("SQLError"), myErrors.getString("SQLErrorWarning"));
        }
    }

    public static User fetchUserFromDatabase(int id){
        try{
            System.out.println("id is " + id);
            String remoteProfilePath = getProfilePath(id);
            System.out.println("remoteProfilePath is " + remoteProfilePath);
            String remoteAvatarPath = getAvatarPath(id);
            System.out.println("remoteAvatarPath is " + remoteAvatarPath);
            String profileFilename = downloadRemoteXML(remoteProfilePath); // filename of local XML
            System.out.println("profileFilenameis is " + profileFilename);
            User u = deserializeUser(profileFilename);
            if (remoteAvatarPath != null){
                String avatarFilename = downloadRemoteAvatar(remoteAvatarPath); // filename of local image
                u.changeAvatar(avatarFilename);
            }
            return u;
        } catch (Exception e){
            throw new UserException(myErrors.getString("SQLError"), myErrors.getString
                    ("SQLErrorWarning"));
        }
    }
}
