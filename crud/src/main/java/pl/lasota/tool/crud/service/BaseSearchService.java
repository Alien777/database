package pl.lasota.tool.crud.service;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import pl.lasota.tool.crud.mapping.SearchMapping;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.SearchRepository;


public class BaseSearchService<READING, MODEL extends EntityBase> implements SearchService<READING, Specification<MODEL>> {

    private SearchRepository<MODEL> repository;
    private SearchMapping<READING, MODEL> mapping;
    private int limit;

    public BaseSearchService(SearchRepository<MODEL> repository, SearchMapping<READING, MODEL> mapping, int limit) {
        this.repository = repository;
        this.mapping = mapping;
        this.limit = limit;
    }

    @Override
    public Page<READING> find(Specification<MODEL> spec, Pageable pageable) {
        return this.mapping.mapping(repository.findAll(spec, pageable));
    }

    @Override
    public Page<READING> find(Specification<MODEL> modelSpecification) {
        return this.mapping.mapping(repository.findAll(modelSpecification, PageRequest.of(0, limit)));
    }

    @Override
    public long count(Specification<MODEL> spec) {
        return repository.count(spec);
    }
}
