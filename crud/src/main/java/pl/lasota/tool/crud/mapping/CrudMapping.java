package pl.lasota.tool.crud.mapping;

import org.springframework.data.domain.Page;

public interface CrudMapping<CREATE, READ, UPDATING, MODEL> {
    MODEL createToModel(CREATE create);

    MODEL updatingToModel(UPDATING model);

    READ modelToRead(MODEL model);

    Page<READ> modelsToPage(Page<MODEL> model);
}
