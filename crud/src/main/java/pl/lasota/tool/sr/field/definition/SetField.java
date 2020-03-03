package pl.lasota.tool.sr.field.definition;

import lombok.ToString;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;

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
