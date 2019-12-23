package pl.lasota.tool.crud.serach.field;

import lombok.ToString;

@ToString
public class NamedField<VALUE> extends Field<VALUE> {
    private final String name;

    NamedField(String name, VALUE value) {
        super(value);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
