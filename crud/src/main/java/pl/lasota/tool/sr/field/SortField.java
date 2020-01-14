package pl.lasota.tool.sr.field;

import lombok.ToString;

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
