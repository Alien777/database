package pl.lasota.tool.sr.field.definition;


import lombok.ToString;
import pl.lasota.tool.sr.field.Normalizer;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;

import java.util.LinkedList;
import java.util.List;

@ToString(callSuper = true)
public abstract class CriteriaField<VALUE> extends NamedField<VALUE> {
    private final Selector selector;
    private final List<Normalizer> normalizers = new LinkedList<>();

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

    public void add(Normalizer normalizer) {
        normalizers.add(normalizer);
    }

    public List<Normalizer> normalizedValue() {
        return normalizers;
    }

    abstract public Operator condition();

}
