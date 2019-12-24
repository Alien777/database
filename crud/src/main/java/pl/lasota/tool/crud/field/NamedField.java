package pl.lasota.tool.crud.field;

public class NamedField<VALUE> extends Field<VALUE> {
    private final String name;

    public NamedField(String name, VALUE value) {
        super(value);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
