package pl.lasota.tool.orm.repository.field;

import lombok.ToString;
import pl.lasota.tool.orm.field.Operator;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.field.Selector;

@ToString(callSuper = true)
public final class SetField extends CriteriaField<String> {
    public SetField(String name, String value) {
        super(name, value, Selector.SET);
    }

    @Override
    public Operator condition() {
        return Operator.SET;
    }
}
