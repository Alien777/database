package pl.lasota.tool.sr.reflection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;


@ToString(callSuper = true)
@AllArgsConstructor
@Getter
public class FieldClass {
    private String name;
    private String path;
    private Class<?> typeField;
    private Class<?> parentClass;
}
