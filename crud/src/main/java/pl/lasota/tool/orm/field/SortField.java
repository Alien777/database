package pl.lasota.tool.orm.field;

import lombok.ToString;
import pl.lasota.tool.orm.common.Sort;

@ToString(callSuper = true)
public final class SortField extends CriteriaField<Sort> {
    public SortField(String name, Sort value) {
        super(name, value, Selector.SORT);
    }

    @Override
    public Operator condition() {
        return Operator.SORT;
    }
}
