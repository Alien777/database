package pl.lasota.database.reflection;

import lombok.ToString;

import java.lang.reflect.Field;


@ToString(callSuper = true)
class ReflectionField {

    public ReflectionField(String path, Field field, Class<?> parentField) {
        this.path = path;
        this.field = field;
        this.parentField = parentField;
    }

    private String path;
    private Field field;
    private Class<?> parentField;

    private boolean covered;
    private boolean projectClass;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public boolean isProjectClass() {
        return projectClass;
    }

    public void setProjectClass(boolean notProjectClass) {
        this.projectClass = notProjectClass;
    }

    public Class<?> getParentField() {
        return parentField;
    }

    public void setParentField(Class<?> parentField) {
        this.parentField = parentField;
    }
}
