package pl.lasota.tool.sr.field.definition;

import lombok.ToString;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;
import pl.lasota.tool.sr.field.Sort;

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
