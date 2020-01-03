package pl.lasota.tool.crud.repository.field;

import pl.lasota.tool.crud.common.Condition;
import pl.lasota.tool.crud.common.CriteriaType;

public final class SetField extends CriteriaField<String> {
    public SetField(String name, String value) {
        super(name, value, CriteriaType.SET);
    }

    @Override
    public Condition condition() {
        return Condition.SET;
    }
}
