package pl.lasota.tool.crud.field;

import lombok.ToString;

@ToString(callSuper = true)
public final class PaginationField extends Field<Pageable> {
    public PaginationField(Pageable value) {
        super(value);
    }
}
