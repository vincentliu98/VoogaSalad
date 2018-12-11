package social;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserParser {
    private List<UserIcon> allUsers;
    private User myUser;

    public UserParser(User user) {
        allUsers = new ArrayList<>();
        myUser = user;
        // TODO: Get all users from database and pass into generateUserIcons
        // FIXME: Erase sample users below (just placeholders)
        Set<User> users = new HashSet<>();
        User u = new User(1, "natalieqle");
        u.changeAvatar("ocean.jpeg");
        u.updateStatus("This is my status!");
        User u2 = new User(2, "bloopy");
        u2.changeAvatar("sunset.jpg");
        u2.updateStatus("This is also my status!");
        users.add(u);
        users.add(u2);
        generateUserIcons(users);
    }

    private void generateUserIcons(Set<User> users) {
        try {
            for (User u : users) {
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


}
