package pl.lasota.tool.crud.repository;

import java.util.Arrays;

public final class AliasNameDiscovery<MODEL> {

    public String discover(String alias, Class<MODEL> model) {
        String nameField = alias;
        java.lang.reflect.Field[] fields = model.getFields();
        for (java.lang.reflect.Field field : fields) {
            AliasName aliasName = field.getAnnotation(AliasName.class);
            if (aliasName != null && Arrays.asList(aliasName.values()).contains(alias)) {
                nameField = field.getName();
            }
        }
        return nameField;
    }
}
