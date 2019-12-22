package pl.lasota.tool.crud.serach.field;

import lombok.ToString;
import pl.lasota.tool.crud.serach.criteria.CriteriaType;

@ToString(callSuper = true)
public final class SetField extends CriteriaField<Sort> {
    public SetField(String name, Sort value) {
        super(name, value, CriteriaType.SORT);
    }

    @Override
    public Condition condition() {
        return Condition.SORT;
    }
}
