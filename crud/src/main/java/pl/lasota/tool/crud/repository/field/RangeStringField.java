package pl.lasota.tool.crud.repository.field;

import pl.lasota.tool.crud.repository.Condition;
import pl.lasota.tool.crud.repository.CriteriaType;

public final class RangeStringField extends CriteriaField<Range<String>> {

    public RangeStringField(String name, Range<String> value, CriteriaType criteriaType) {
        super(name, value, criteriaType);
    }

    @Override
    public Condition condition() {
        return Condition.BETWEEN;
    }
}
