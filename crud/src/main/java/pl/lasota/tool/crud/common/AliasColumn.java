package pl.lasota.tool.crud.common;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AliasColumn {

    String[] names();

}
