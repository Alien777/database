package pl.lasota.tool.sr.field;

public class AllOneEqualOne extends CriteriaField<Object> {
    public AllOneEqualOne() {
        super("literal_field", new Object(), Selector.AND);
    }

    @Override
    public Operator condition() {
        return Operator.ALL;
    }
}
