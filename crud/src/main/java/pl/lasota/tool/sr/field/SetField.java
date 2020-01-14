package pl.lasota.tool.sr.field;

import lombok.ToString;

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
