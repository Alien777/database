package pl.lasota.tool.crud.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import pl.lasota.tool.crud.mapping.SearchMapping;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.SearchRepository;
import pl.lasota.tool.crud.serach.criteria.BuildingCriteriaSpecification;
import pl.lasota.tool.crud.serach.criteria.DistributeField;
import pl.lasota.tool.crud.serach.field.CriteriaField;
import pl.lasota.tool.crud.serach.field.Field;
import pl.lasota.tool.crud.serach.criteria.Mapper;
import pl.lasota.tool.crud.serach.field.PaginationField;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CriteriaSearchService<READING, MODEL extends EntityBase> implements SearchService<READING, List<Field>> {

    private final Mapper<MODEL> mapper;

    private final SearchService<READING, Specification<MODEL>> searchService;

    public CriteriaSearchService(SearchRepository<MODEL> repository, SearchMapping<READING, MODEL> mapping, Mapper<MODEL> mapper, int limit) {
        searchService = new BaseSearchService<>(repository, mapping, limit);
        this.mapper = mapper;
    }

    @Override
    public Page<READING> find(List<Field> formula, Pageable pageable) {
        return searchService.find(createCriteriaSearch(formula), pageable);
    }

    @Override
    public Page<READING> find(List<Field> formula) {
        Optional<PaginationField> first = formula.stream()
                .filter(field -> field instanceof PaginationField)
                .map(field -> (PaginationField) field).findFirst();

        if (first.isPresent()) {
            pl.lasota.tool.crud.serach.field.Page value = first.get().getValue();
            return searchService.find(createCriteriaSearch(formula), PageRequest.of(value.getPage(), value.getLimit()));
        }
        return searchService.find(createCriteriaSearch(formula));
    }

    @Override
    public long count(List<Field> formula) {
        return searchService.count(createCriteriaSearch(formula));
    }

    private BuildingCriteriaSpecification<MODEL> createCriteriaSearch(List<Field> formula) {
        List<CriteriaField> collect = formula.stream()
                .filter(field -> field instanceof CriteriaField)
                .map(field -> (CriteriaField) field).collect(Collectors.toList());

        DistributeField<MODEL> distributeField = new DistributeField<>(collect, mapper);
        return new BuildingCriteriaSpecification<>(distributeField);
    }

}
