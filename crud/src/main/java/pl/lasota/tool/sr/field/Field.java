package pl.lasota.tool.sr.field;

import lombok.ToString;

@ToString(callSuper = true)
public class Field<VALUE> {
    private final VALUE value;

    public Field(VALUE value) {
        this.value = value;
    }
    public VALUE getValue() {
        return value;
    }
}
