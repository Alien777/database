package pl.lasota.tool.sr.field.definition;

import lombok.ToString;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;

@ToString(callSuper = true)
public final class SimpleField extends CriteriaField<String> {
    private final Operator operator;

    public SimpleField(String name, String value, Selector selector, Operator operator) {
        super(name, value, selector);
        this.operator = operator;
    }

    @Override
    public Operator condition() {
        return operator;
    }
}
