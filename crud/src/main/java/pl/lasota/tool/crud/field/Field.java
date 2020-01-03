package pl.lasota.tool.crud.field;

import lombok.ToString;

@ToString
public class Field<VALUE> {
    private final VALUE value;

    public Field(VALUE value) {
        this.value = value;
    }
    public VALUE getValue() {
        return value;
    }
}
