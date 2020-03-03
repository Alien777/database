package pl.lasota.tool.sr.field.definition;

import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;

public class AllOneEqualOne extends CriteriaField<Object> {
    public AllOneEqualOne() {
        super("literal_field", new Object(), Selector.AND);
    }

    @Override
    public Operator condition() {
        return Operator.GET_ALL;
    }
}
