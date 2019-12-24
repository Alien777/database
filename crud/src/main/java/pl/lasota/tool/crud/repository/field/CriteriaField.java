package pl.lasota.tool.crud.repository.field;

import lombok.ToString;
import pl.lasota.tool.crud.field.NamedField;
import pl.lasota.tool.crud.repository.Condition;
import pl.lasota.tool.crud.repository.CriteriaType;

@ToString
public abstract class CriteriaField<VALUE> extends NamedField<VALUE> {
    private final CriteriaType criteriaType;

    public CriteriaField(String name, VALUE value, CriteriaType criteriaType) {
        super(name,value);
        this.criteriaType = criteriaType;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    abstract public Condition condition();

}
