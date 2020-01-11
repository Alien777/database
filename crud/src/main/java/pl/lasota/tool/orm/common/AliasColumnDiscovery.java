package pl.lasota.tool.orm.common;

import org.springframework.data.util.Pair;
import pl.lasota.tool.orm.reflection.UtilsReflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class AliasColumnDiscovery<MODEL> {


    private final Map<String, String[]> map = new HashMap<>();

    public AliasColumnDiscovery(Class<MODEL> model) {

        UtilsReflection.findAllFieldsContains(model, AliasColumn.class, fieldNode -> {
            Pair<String, Field> pathWithFile = UtilsReflection.getPathWithFile(fieldNode);
            AliasColumn annotation = fieldNode.getField().getAnnotation(AliasColumn.class);
            map.put(pathWithFile.getFirst(), annotation.names());
        });
    }

    public String discover(String alias) {
        return alias;
    }
}
