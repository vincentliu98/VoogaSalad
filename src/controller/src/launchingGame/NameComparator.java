package launchingGame;

import social.Icon;

import java.util.Comparator;

public class NameComparator implements Comparator<Icon> {

    @Override
    public int compare(Icon o1, Icon o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }

}
