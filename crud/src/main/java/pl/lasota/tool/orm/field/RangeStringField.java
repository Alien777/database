package pl.lasota.tool.orm.field;

public final class RangeStringField extends CriteriaField<Range<String>> {

    public RangeStringField(String name, Range<String> value, CriteriaType criteriaType) {
        super(name, value, criteriaType);
    }

    @Override
    public Condition condition() {
        return Condition.BETWEEN;
    }
}
