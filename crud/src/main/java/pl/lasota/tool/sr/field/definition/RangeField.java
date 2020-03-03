package pl.lasota.tool.sr.field.definition;

import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Range;
import pl.lasota.tool.sr.field.Selector;

public final class RangeField extends CriteriaField<Range<String>> {

    public RangeField(String name, Range<String> value, Selector selector) {
        super(name, value, selector);
    }

    @Override
    public Operator condition() {
        return Operator.RANGE;
    }
}
