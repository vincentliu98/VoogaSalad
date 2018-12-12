package social;

import exceptions.ErrorMessage;
import exceptions.UserException;
import util.data.DatabaseDownloader;
import java.sql.ResultSet;

import java.util.*;

public class UserParser {
    private List<UserIcon> allUsers;
    private User myUser;
    private ResourceBundle myErrors = ResourceBundle.getBundle("Errors");

    public UserParser(User user) {
        allUsers = new ArrayList<>();
        myUser = user;
        generateUserIcons(getUsers());
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
                System.out.println("The id, in userparser, is " + id);
                User u = DatabaseHelper.fetchUserFromDatabase(id);
                users.add(u);
            }
        } catch (Exception e){
            new ErrorMessage(new UserException(myErrors.getString("SQLError"), myErrors.getString("SQLErrorWarning")));
        }
        return users;
    }

    private void generateUserIcons(Set<User> users) {
        for (User u : users) {
            UserIcon userIcon;
            if (myUser == null) {
                System.out.println("u is " + u);
                userIcon = new UserIcon(u.getUsername(), u.getStatus(), "", "",
                        u.getImageReference(), "", u, "", "");
            } else {
                userIcon = new UserIcon(u.getUsername(), u.getStatus(), "", "",
                        u.getImageReference(), "", myUser, "", "");
            }
            allUsers.add(userIcon);
        }
    }

    public List<UserIcon> getAllUsers() {
        return allUsers;
    }
}
