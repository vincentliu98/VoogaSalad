package social;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import util.data.DatabaseDownloader;
import util.files.ServerDownloader;
import util.files.ServerUploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserIcon extends Icon {

    public static final String UNFOLLOW_TEXT = "Following";
    private boolean isFollowing;

    public UserIcon(String gameName, String description, String reference, String color, String imagePath,
                    String tags, User user, String buttonText, String imgFolderPath) {
        super(gameName, description, reference, color, imagePath, tags, user, "Follow", "src/controller/resources" +
                "/profile-images/");
        myButtonHolder.setAlignment(Pos.BOTTOM_RIGHT);
        changeButtonDisplay();
    }

    @Override
    public void initButtonHandlers() {
        myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (myUser == null || myUser.getUsername().equals(myName)) { // TODO: Turn into an error
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Cannot follow others without a profile.");
                    alert.setContentText("Log in or create a new profile.");
                    alert.showAndWait();
                } else {
                    isFollowing = !isFollowing;
                    changeButtonDisplay();
                }
            }
        });
    }

    protected void initPane() {
        myPane = new StackPane();

        myPane.setOnMouseEntered(event -> { // always want username
            myPane.getChildren().add(myDescriptionHolder);
            myPane.getChildren().add(myButtonHolder);
        });

        myPane.setOnMouseExited(event -> {
            myPane.getChildren().remove(myDescriptionHolder);
            myPane.getChildren().remove(myButtonHolder);
        });

    }

    private void changeButtonDisplay() {
        DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        ResultSet result = databaseDownloader.queryServer(String.format("SELECT id FROM logins WHERE " +
                "username='%s'", myName));
        try {
            result.last();
            int id = result.getInt("id");
            String remoteProfilePath = getProfilePath(id);
            String profileFilename = downloadRemoteXML(remoteProfilePath); // filename of local XML (to be deleted)
            User u = deserializeUser(profileFilename);
            if (isFollowing) {
                myButton.getStyleClass().remove(BUTTON_CSS_HOVER);
                myButton.getStyleClass().add(BUTTON_CSS_NORMAL);
                myButton.setText(UNFOLLOW_TEXT);
                myUser.follow(myName);
                u.addFollower(myUser.getUsername());
                uploadSerializedUserFile(u);
                // get MyName from database and follow

            } else {
                myButton.getStyleClass().remove(BUTTON_CSS_NORMAL);
                myButton.getStyleClass().add(BUTTON_CSS_HOVER);
                myButton.setText(BUTTON_TEXT);
                myUser.unfollow(myName);
                u.removeFollower(myUser.getUsername());
                uploadSerializedUserFile(u);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private String getProfilePath(int id) throws SQLException {
        DatabaseDownloader databaseDownloader = new DatabaseDownloader("client", "store",
                "e.printstacktrace", "vcm-7456.vm.duke.edu", 3306);
        ResultSet result = databaseDownloader.queryServer(String.format("SELECT profilePath FROM" +
                " userReferences WHERE id='%d'", id));
        result.last();
        return result.getString("profilePath");
    }

    private String downloadRemoteXML(String profilePath) throws SQLException {
        ServerDownloader downloader = new ServerDownloader();
        downloader.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
        File directory = new File("src/database/resources/");
        downloader.downloadFile(profilePath,directory.getAbsolutePath());
        String[] filePathArray = profilePath.split("/");
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

    private void uploadSerializedUserFile(User user) throws IOException {
        XStream serializer = new XStream(new DomDriver());
        File userFile=new File("src/database/resources/" + user.getUsername() + ".xml");
        if (!userFile.exists()){
            userFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(userFile);
        fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + serializer.toXML(user));
        fileWriter.close();
        ServerUploader upload = new ServerUploader();
        upload.connectServer("vcm", "vcm-7456.vm.duke.edu", 22,"afcas8amYf");
        upload.uploadFile(userFile.getAbsolutePath(), "/users/profiles");
        userFile.delete();
    }
}
