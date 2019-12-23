package pl.lasota.tool.crud.serach.field;

import lombok.ToString;
import pl.lasota.tool.crud.repository.search.criteria.CriteriaType;

@ToString
public abstract class CriteriaField<VALUE> extends NamedField<VALUE> {
    private final CriteriaType criteriaType;

    CriteriaField(String name, VALUE value, CriteriaType criteriaType) {
        super(name, value);
        this.criteriaType = criteriaType;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    abstract public Condition condition();

}
