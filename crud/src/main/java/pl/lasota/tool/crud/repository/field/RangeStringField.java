package pl.lasota.tool.crud.repository.field;

import lombok.ToString;
import pl.lasota.tool.crud.repository.Condition;
import pl.lasota.tool.crud.repository.CriteriaType;

@ToString(callSuper = true)
public final class RangeStringField extends CriteriaField<Range<String>> {

    public RangeStringField(String name, Range<String> value, CriteriaType criteriaType) {
        super(name, value, criteriaType);
    }

    @Override
    public Condition condition() {
        return Condition.BETWEEN;
    }
}
