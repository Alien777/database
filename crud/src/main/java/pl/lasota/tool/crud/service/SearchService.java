package pl.lasota.tool.crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;


public interface SearchService<READ, MODEL> {

    List<READ> findAll(@Nullable Specification<MODEL> spec);


    Page<READ> findAll(@Nullable Specification<MODEL> spec, Pageable pageable);


    List<READ> findAll(@Nullable Specification<MODEL> spec, Sort sort);

    long count(@Nullable Specification<MODEL> spec);
}
