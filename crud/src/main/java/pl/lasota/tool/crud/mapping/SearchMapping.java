package pl.lasota.tool.crud.mapping;

import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchMapping<READ, MODEL> {
    Page<READ> mapping(Page<MODEL> model);

    List<READ> mapping(List<MODEL> model);
}
