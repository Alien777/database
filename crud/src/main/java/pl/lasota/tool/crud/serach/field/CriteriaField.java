package pl.lasota.tool.crud.serach.field;

import lombok.ToString;
import pl.lasota.tool.crud.repository.search.criteria.CriteriaType;

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
