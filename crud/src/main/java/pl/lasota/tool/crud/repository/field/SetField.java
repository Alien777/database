package pl.lasota.tool.crud.repository.field;

import lombok.ToString;
import pl.lasota.tool.crud.repository.Condition;
import pl.lasota.tool.crud.repository.CriteriaType;

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
