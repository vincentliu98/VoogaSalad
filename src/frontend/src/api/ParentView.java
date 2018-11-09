package api;

/**
 * This interface allows graphical elements to have "children" elements and the definition of those children elements are defined by the specific classes implementing this interface. This interface just serves as an abstract concept in which an element encompasses another element. For example, a floating window is a child to the main window. Menu items are children to a menu.
 *
 * @author Haotian Wang
 */
@FunctionalInterface
public interface ParentView {
    void addChild(SubView view);
}
