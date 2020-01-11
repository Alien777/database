package pl.lasota.tool.orm.common;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AliasColumn {

    String[] names();

}
