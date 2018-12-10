package groovy.graph.blocks.small_factory;

import authoringUtils.frontendUtils.Try;
import gameObjects.crud.GameObjectsCRUDInterface;

import java.util.Arrays;

public class ValidationUtil {
    public static Try<String> validateReference(String ref, GameObjectsCRUDInterface entityDB) {
        var split = Arrays.asList(ref.split("\\."));

//        // does that property even exist?
//        int propsIdx = split.indexOf("props");
//        if(0 <= propsIdx && propsIdx < split.size()-1) {
//            var propName = split.get(propsIdx+1).trim();
//            var propertyExists =
//                entityDB.getAllClasses().stream()
//                        .flatMap(c -> c.getPropertiesMap().keySet().stream())
//                        .anyMatch(propName::equals);
//            if(!propertyExists) return Try.failure(new NoSuchPropertyException(propName));
//        }

        return Try.success(ref);
    }
}
