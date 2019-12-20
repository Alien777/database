package pl.lasota.tool.crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CrudService<CREATE, READ, UPDATING, ID> {

    READ save(CREATE create);

    READ get(ID id);

    Page<READ> getAll(Pageable criteria);

    void delete(ID id);

    READ update(UPDATING updating);
}
