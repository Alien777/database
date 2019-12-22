package pl.lasota.tool.crud.serach.field;

import lombok.ToString;
import pl.lasota.tool.crud.serach.criteria.CriteriaType;

@ToString(callSuper = true)
public final class PaginationField extends Field<Page> {
    public PaginationField(Page value) {
        super(value);
    }
}
