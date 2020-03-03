package pl.lasota.tool.sr.field.definition;

import lombok.ToString;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;

@ToString(callSuper = true)
public final class MultipleValuesField extends CriteriaField<String[]> {
    private final Operator operator;

    public static MultipleValuesField shouldBeOneOfThem(String name, Operator operator, String[] value) {
        return new MultipleValuesField(name, value, Selector.AND, operator);
    }

    private MultipleValuesField(String name, String[] value, Selector selector, Operator operator) {
        super(name, value, selector);
        this.operator = operator;
    }

    @Override
    public Operator condition() {
        return operator;
    }
}
