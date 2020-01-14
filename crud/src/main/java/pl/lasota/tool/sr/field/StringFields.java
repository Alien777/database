package pl.lasota.tool.sr.field;

import lombok.ToString;

@ToString(callSuper = true)
public final class StringFields extends CriteriaField<String[]> {
    private final Operator operator;

    public static StringFields shouldBeOneOfThem(String name, Operator operator, String[] value) {
        return new StringFields(name, value, Selector.AND, operator);
    }

    private StringFields(String name, String[] value, Selector selector, Operator operator) {
        super(name, value, selector);
        this.operator = operator;
    }

    @Override
    public Operator condition() {
        return operator;
    }
}
