package pl.lasota.tool.crud.serach.field;

import lombok.ToString;
import pl.lasota.tool.crud.serach.criteria.CriteriaType;

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
