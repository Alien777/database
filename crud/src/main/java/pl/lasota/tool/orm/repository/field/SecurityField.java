package pl.lasota.tool.orm.repository.field;

import lombok.ToString;
import pl.lasota.tool.orm.common.Condition;
import pl.lasota.tool.orm.common.CriteriaType;
import pl.lasota.tool.orm.common.Sort;

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
