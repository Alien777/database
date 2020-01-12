package pl.lasota.tool.orm.field;

import lombok.ToString;

@ToString(callSuper = true)
public final class StringFields extends CriteriaField<String[]> {
    private final Condition condition;

    public static StringFields or(String name, Condition condition, String[] value) {
        return new StringFields(name, value, CriteriaType.AND, condition);
    }

    private StringFields(String name, String[] value, CriteriaType criteriaType, Condition condition) {
        super(name, value, criteriaType);
        this.condition = condition;
    }

    @Override
    public Condition condition() {
        return condition;
    }
}
