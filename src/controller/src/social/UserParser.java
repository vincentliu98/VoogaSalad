package social;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.image.Image;
import util.data.DatabaseDownloader;
import util.files.ServerDownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserParser {
    private List<UserIcon> allUsers;
    private User myUser;

    public UserParser(User user) {
        allUsers = new ArrayList<>();
        myUser = user;
        generateUserIcons(getUsers());
//        EventBus.getInstance().register(EngineEvent.CHANGE_USER, this::reassignUser);
//        EventBus.getInstance().register(EngineEvent.LOGGED_OUT, this::resetUser);
    }

    private Set<User> getUsers(){
        Set<User> users = new HashSet<>();
        DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        ResultSet result;
        if (myUser != null){
            result = databaseDownloader.queryServer(String.format("SELECT id FROM logins WHERE id!='%d'", myUser.getID()));
        } else {
            result = databaseDownloader.queryServer("SELECT id FROM logins");
        }
        try{
            while (result.next()){
                int id = result.getInt("id");
                if (myUser != null){
                    System.out.println("mID is " + myUser.getID() + "and id of remote user is " + id);
                } else {
                    System.out.println("mID is null and id of remote user is " + id);
                }
                String remoteProfilePath = getProfilePath(id);
                String remoteAvatarPath = getAvatarPath(id);
                String profileFilename = downloadRemoteXML(remoteProfilePath); // filename of local XML (to be deleted)
                User u = deserializeUser(profileFilename);
                if (remoteAvatarPath != null){
                    String avatarFilename = downloadRemoteAvatar(remoteAvatarPath); // filename of local image
                    u.changeAvatar(avatarFilename);
                }
                users.add(u);
            }
        } catch (Exception e){

        }
        return users;
    }

    private void generateUserIcons(Set<User> users) {
        try {
            for (User u : users) {
                System.out.println("Creating icon for user " + u.getID());
                UserIcon userIcon;
                if (myUser == null) {
                    userIcon = new UserIcon(u.getUsername(), u.getStatus(), "", "",
                            u.getImageReference(), "", u, "", "");
                } else {
                    userIcon = new UserIcon(u.getUsername(), u.getStatus(), "", "",
                            u.getImageReference(), "", myUser, "", "");
                }
                allUsers.add(userIcon);
            }
        } catch (Exception e) {
        }
    }

    public List<UserIcon> getAllUsers() {
        return allUsers;
    }
//
//    private void reassignUser(Object... args) {
//        myUser = (User) args[0];
//        generateUserIcons(getUsers());
//    }
//
//    private void resetUser(Object... args) {
//        myUser = null;
//    }

    private String getProfilePath(int id) throws SQLException {
        DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        ResultSet result = databaseDownloader.queryServer(String.format("SELECT profilePath FROM" +
                " userReferences WHERE id='%d'", id));
        result.last();
        return result.getString("profilePath");
    }

    private String getAvatarPath(int id) throws SQLException {
        DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        ResultSet result = databaseDownloader.queryServer(String.format("SELECT avatarPath FROM" +
                " userReferences WHERE id='%d'", id));
        result.last();
        return result.getString("avatarPath");
    }

    private String downloadRemoteXML(String profilePath) throws SQLException {
        ServerDownloader downloader = new ServerDownloader();
        downloader.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
        File directory = new File("src/database/resources/");
        downloader.downloadFile(profilePath,directory.getAbsolutePath());
        String[] filePathArray = profilePath.split("/");
        return filePathArray[filePathArray.length - 1];
    }

    private String downloadRemoteAvatar(String avatarPath) throws SQLException {
        ServerDownloader downloader = new ServerDownloader();
        downloader.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
        File directory = new File("src/controller/resources/profile-images/");
        downloader.downloadFile(avatarPath, directory.getAbsolutePath());
        String[] filePathArray = avatarPath.split("/");
        return filePathArray[filePathArray.length - 1];
    }

    private User deserializeUser(String fileName) throws FileNotFoundException {
        XStream serializer = new XStream(new DomDriver());
        File file = new File("src/database/resources/" + fileName);
        Scanner scanner = new Scanner(file, "UTF-8" );
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        text = text.trim().replaceFirst("^([\\W]+)<","<");
        file.delete();
        return (User) serializer.fromXML(text);
    }
}
