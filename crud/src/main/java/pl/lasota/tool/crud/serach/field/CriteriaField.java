package pl.lasota.tool.crud.serach.field;

import lombok.ToString;
import lombok.Value;
import pl.lasota.tool.crud.serach.criteria.CriteriaType;

@ToString
public abstract class CriteriaField<VALUE> extends Field<VALUE> {
    private final String name;
    private final CriteriaType criteriaType;

    CriteriaField(String name, VALUE value, CriteriaType criteriaType) {
        super(value);
        this.name = name;
        this.criteriaType = criteriaType;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public String getName() {
        return name;
    }

    abstract public Condition condition();

}
