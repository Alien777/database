package pl.lasota.tool.crud.reflection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Objects;


@AllArgsConstructor
@ToString(callSuper = true)
public class FieldNode {

    private final FieldNode next;
    private final Field field;
    private boolean contains;

    public FieldNode(FieldNode next, Field field) {
        this.next = next;
        this.field = field;
    }

    public void setContains(boolean contains) {
        this.contains = contains;
    }

    public FieldNode getNext() {
        return next;
    }

    public Field getField() {
        return field;
    }

    public boolean isContains() {
        return contains;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldNode fieldNode = (FieldNode) o;
        return Objects.equals(next, fieldNode.next) &&
                Objects.equals(field, fieldNode.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, field);
    }
}
