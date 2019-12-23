package pl.lasota.tool.crud.repository.annotaction;

import java.util.Arrays;

public final class AliasColumnDiscovery<MODEL> {

    public String discover(String alias, Class<MODEL> model) {
        String aliasName = alias;
        java.lang.reflect.Field[] fields = model.getFields();
        for (java.lang.reflect.Field field : fields) {
            AliasColumn aliasColumn = field.getAnnotation(AliasColumn.class);
            if (aliasColumn != null && Arrays.asList(aliasColumn.names()).contains(alias)) {
                aliasName = field.getName();
            }
        }
        return aliasName;
    }
}
