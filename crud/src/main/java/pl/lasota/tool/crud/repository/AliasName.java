package pl.lasota.tool.crud.repository;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AliasName {
    String[] values();
}
