package pl.lasota.tool.crud.repository.field;

import lombok.ToString;
import pl.lasota.tool.crud.repository.Condition;
import pl.lasota.tool.crud.repository.CriteriaType;

@ToString(callSuper = true)
public final class SortField extends CriteriaField<Sort> {
    public SortField(String name, Sort value) {
        super(name, value, CriteriaType.SORT);
    }

    @Override
    public Condition condition() {
        return Condition.SORT;
    }
}
