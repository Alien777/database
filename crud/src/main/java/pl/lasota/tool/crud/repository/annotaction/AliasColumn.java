package pl.lasota.tool.crud.repository.annotaction;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AliasColumn {

    String[] names();

}
