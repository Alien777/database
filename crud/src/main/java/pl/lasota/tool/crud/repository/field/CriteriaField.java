package pl.lasota.tool.crud.repository.field;


import lombok.ToString;
import pl.lasota.tool.crud.field.NamedField;
import pl.lasota.tool.crud.common.Condition;
import pl.lasota.tool.crud.common.CriteriaType;
@ToString(callSuper = true)
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
