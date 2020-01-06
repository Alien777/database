package pl.lasota.tool.crud.repository.field;

import lombok.ToString;
import pl.lasota.tool.crud.common.Condition;
import pl.lasota.tool.crud.common.CriteriaType;
import pl.lasota.tool.crud.common.Sort;

@ToString(callSuper = true)
public final class SecurityField extends CriteriaField<Sort> {
    public SecurityField(String name, Sort value) {
        super(name, value, CriteriaType.SORT);
    }

    @Override
    public Condition condition() {
        return Condition.SORT;
    }
}
