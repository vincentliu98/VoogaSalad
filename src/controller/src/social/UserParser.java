package social;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserParser {
    private List<UserIcon> myUsers;

    public UserParser(){
        myUsers = new ArrayList<>();
        // TODO: Get all users from database and pass into generateUserIcons
        Set<User> users = new HashSet<>();
        User u = new User(1, "natalieqle");
        u.changeAvatar(new Image("duke_logo.png"));
        users.add(u);
        generateUserIcons(users);
    }

    private void generateUserIcons(Set<User> users){
        try{
            for (User u : users){
                UserIcon userIcon = new UserIcon(u.getUsername(), u.getAvatar(), true);
                myUsers.add(userIcon);
            }
        }
        catch (Exception e){ }
    }

    public List<UserIcon> getMyUsers(){
        return myUsers;
    }


}
