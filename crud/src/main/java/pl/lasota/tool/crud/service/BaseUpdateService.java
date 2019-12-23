package pl.lasota.tool.crud.service;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.*;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.Specification;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;
import pl.lasota.tool.crud.repository.search.criteria.DistributeFieldFactory;
import pl.lasota.tool.crud.repository.search.criteria.FieldMapperFields;
import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.repository.update.criteria.UpdateCriteriaSpecification;
import pl.lasota.tool.crud.serach.field.Field;

import java.util.List;

@AllArgsConstructor
public class BaseUpdateService<READING, MODEL extends EntityBase> implements UpdateService<READING, MODEL> {

    private final UpdateRepository<MODEL> repository;
    private final Mapping<List<MODEL>, List<READING>> mapping;
    private final Class<MODEL> modelClass;

    @Override
    @Transactional
    public List<READING> update(List<Field<?>> source) {
        List<MODEL> update = repository.update(providerSpecification(source));
        return mapping.mapper(update);
    }


    @Override
    public SpecificationUpdate<MODEL> providerSpecification(List<Field<?>> fields) {
        return new UpdateCriteriaSpecification<>(new DistributeFieldFactory<>(filter(fields), new FieldMapperFields<>(), modelClass));
    }

}
