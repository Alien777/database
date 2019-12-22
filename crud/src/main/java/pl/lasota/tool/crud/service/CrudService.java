package pl.lasota.tool.crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;


public interface CrudService<CREATING, READING, UPDATING> {

    READING save(@Nullable CREATING CREATING);

    READING get(@Nullable Long id);

    void delete(@Nullable Long id);

    READING update(@Nullable UPDATING updating);
}
