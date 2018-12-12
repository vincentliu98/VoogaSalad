package utility;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

import java.util.function.Function;
import java.util.stream.Collectors;

public class ObservableUtils {
    public static <T, V> void bindList(ObservableList<T> tList, ObservableList<V> vList, Function<T, V> mapping) {
        tList.addListener((ListChangeListener<? super T>) e -> {
            while (e.next()) {
                if (e.wasAdded()) {
                    if (e.wasAdded()) {
                        var addedNames = e.getAddedSubList().stream().map(mapping).collect(Collectors.toList());
                        vList.addAll(addedNames);
                    } else if (e.wasRemoved()) {
                        var removedNames = e.getRemoved().stream().map(mapping).collect(Collectors.toList());
                        vList.removeAll(removedNames);
                    }
                }
            }
        });
    }

    public static <T, V> void bindSet(ObservableSet<T> tSet, ObservableSet<V> vSet, Function<T, V> mapping) {
        tSet.addListener((SetChangeListener<? super T>) e -> {
            if (e.wasAdded()) {
                if (e.wasAdded()) {
                    vSet.add(mapping.apply(e.getElementAdded()));
                } else if (e.wasRemoved()) {
                    vSet.remove(mapping.apply(e.getElementRemoved()));
                }
            }
        });
    }
}
