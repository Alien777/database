package pl.lasota.tool.crud.serach.field;

import lombok.ToString;
import pl.lasota.tool.crud.serach.criteria.CriteriaType;

@ToString
public class Field<VALUE> {
    private final VALUE value;

    Field(VALUE value) {
        this.value = value;
    }
    public VALUE getValue() {
        return value;
    }
}
