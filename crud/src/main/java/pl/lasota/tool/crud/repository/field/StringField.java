package pl.lasota.tool.crud.repository.field;

import pl.lasota.tool.crud.repository.Condition;
import pl.lasota.tool.crud.repository.CriteriaType;

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
