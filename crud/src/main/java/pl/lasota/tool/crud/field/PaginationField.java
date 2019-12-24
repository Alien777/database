package pl.lasota.tool.crud.field;

import lombok.ToString;

@ToString(callSuper = true)
public final class PaginationField extends Field<Page> {
    public PaginationField(Page value) {
        super(value);
    }
}
