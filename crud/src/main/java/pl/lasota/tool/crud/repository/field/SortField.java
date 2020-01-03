package pl.lasota.tool.crud.repository.field;

import lombok.ToString;
import pl.lasota.tool.crud.common.Sort;
import pl.lasota.tool.crud.common.Condition;
import pl.lasota.tool.crud.common.CriteriaType;
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
