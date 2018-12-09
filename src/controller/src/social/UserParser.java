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
        User u2 = new User(1, "bloopy");
        u.changeAvatar(new Image("square.png"));
        users.add(u);
        users.add(u2);
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
