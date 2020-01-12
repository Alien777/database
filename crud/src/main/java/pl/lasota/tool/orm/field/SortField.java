package pl.lasota.tool.orm.field;

import lombok.ToString;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.common.Sort;
import pl.lasota.tool.orm.field.Condition;
import pl.lasota.tool.orm.field.CriteriaType;
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
