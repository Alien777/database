package pl.lasota.tool.orm.field;

public final class RangeStringField extends CriteriaField<Range<String>> {

    public RangeStringField(String name, Range<String> value, Selector selector) {
        super(name, value, selector);
    }

    @Override
    public Operator condition() {
        return Operator.BETWEEN;
    }
}
