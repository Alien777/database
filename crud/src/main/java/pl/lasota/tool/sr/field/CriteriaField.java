package pl.lasota.tool.sr.field;


import lombok.ToString;

@ToString(callSuper = true)
public abstract class CriteriaField<VALUE> extends NamedField<VALUE> {
    private final Selector selector;

    public CriteriaField(String name, VALUE value, Selector selector) {
        super(name, value);
        this.selector = selector;
    }

    public CriteriaField<VALUE> copy(String newName, Operator operator) {
        return new CriteriaField<VALUE>(newName, super.getValue(), selector) {
            @Override
            public Operator condition() {
                return operator;
            }
        };
    }

    public Selector getSelector() {
        return selector;
    }

    abstract public Operator condition();

}
