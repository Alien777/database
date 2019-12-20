package pl.lasota.tool.crud;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import pl.lasota.tool.crud.mapping.SearchMapping;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.SearchRepository;
import pl.lasota.tool.crud.service.SearchService;

import java.util.List;

public class MapperSearchService<READ, MODEL extends EntityBase, ID extends Number> implements SearchService<READ, MODEL> {

    private SearchRepository<MODEL, ID> repository;
    private SearchMapping<READ, MODEL> mapping;

    public MapperSearchService(SearchRepository<MODEL, ID> repository, SearchMapping<READ, MODEL> mapping) {
        this.repository = repository;
        this.mapping = mapping;
    }

    @Override
    public List<READ> findAll(Specification<MODEL> spec) {
        List<MODEL> all = repository.findAll(spec);
        return this.mapping.mapping(all);
    }

    @Override
    public Page<READ> findAll(Specification<MODEL> spec, Pageable pageable) {
        Page<MODEL> all = repository.findAll(spec, pageable);
        return this.mapping.mapping(all);
    }

    @Override
    public List<READ> findAll(Specification<MODEL> spec, Sort sort) {
        List<MODEL> all = repository.findAll(spec, sort);
        return this.mapping.mapping(all);
    }

    @Override
    public long count(Specification<MODEL> spec) {
        return repository.count(spec);
    }
}
