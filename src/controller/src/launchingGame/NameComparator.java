package launchingGame;

import java.util.Comparator;

public class NameComparator implements Comparator<GameIcon> {

    @Override
    public int compare(GameIcon o1, GameIcon o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
