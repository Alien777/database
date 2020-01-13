package pl.lasota.tool.orm.field;

import lombok.ToString;

@ToString(callSuper = true)
public final class   StringField extends CriteriaField<String> {
    private final Operator operator;

    public StringField(String name, String value, Selector selector, Operator operator) {
        super(name, value, selector);
        this.operator = operator;
    }

    @Override
    public Operator condition() {
        return operator;
    }
}
