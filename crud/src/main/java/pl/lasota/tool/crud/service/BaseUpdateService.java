package pl.lasota.tool.crud.service;


import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.FieldMapperFields;
import pl.lasota.tool.crud.repository.distributed.DistributeFactory;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.repository.update.criteria.UpdateCriteriaSpecification;

import java.util.List;

public class BaseUpdateService<READING, MODEL extends EntityBase> implements UpdateService<READING, MODEL> {

    private final UpdateRepository<MODEL> repository;
    private final Mapping<List<MODEL>, List<READING>> mapping;
    private final Class<MODEL> modelClass;

    public BaseUpdateService(UpdateRepository<MODEL> repository, Mapping<List<MODEL>, List<READING>> mapping, Class<MODEL> modelClass) {
        this.repository = repository;
        this.mapping = mapping;
        this.modelClass = modelClass;
    }

    @Override
    @Transactional
    public List<READING> update(List<Field<?>> source) {
        List<MODEL> update = repository.update(providerSpecification(source));
        return mapping.mapper(update);
    }


    @Override
    public SpecificationUpdate<MODEL> providerSpecification(List<Field<?>> fields) {
        return new UpdateCriteriaSpecification<>(new DistributeFactory<>(filter(fields), new FieldMapperFields<>()), modelClass);
    }

}
