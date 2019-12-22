package pl.lasota.tool.crud.serach.field;

import lombok.ToString;
import pl.lasota.tool.crud.serach.criteria.CriteriaType;

@ToString(callSuper = true)
public final class StringField extends CriteriaField<String> {
    private final Condition condition;

    public StringField(String name, String value, CriteriaType criteriaType, Condition condition) {
        super(name, value, criteriaType);
        this.condition = condition;
    }

    @Override
    public Condition condition() {
        return condition;
    }
}
