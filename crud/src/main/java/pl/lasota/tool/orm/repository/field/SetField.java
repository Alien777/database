package pl.lasota.tool.orm.repository.field;

import lombok.ToString;
import pl.lasota.tool.orm.field.Condition;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.field.CriteriaType;

@ToString(callSuper = true)
public final class SetField extends CriteriaField<String> {
    public SetField(String name, String value) {
        super(name, value, CriteriaType.SET);
    }

    @Override
    public Condition condition() {
        return Condition.SET;
    }
}
