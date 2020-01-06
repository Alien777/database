package pl.lasota.tool.crud.service.base;


import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.mapping.FieldMapping;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.repository.update.criteria.UpdateCriteriaSpecification;
import pl.lasota.tool.crud.service.SpecificationProvider;
import pl.lasota.tool.crud.service.UpdateService;

import java.util.List;

public class BaseUpdateService<MODEL extends EntityBase> implements UpdateService, SpecificationProvider<Specification<MODEL>> {

    private final UpdateRepository<MODEL> repository;

    private final FieldMapping<MODEL> map;

    public BaseUpdateService(UpdateRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        map = new FieldMapping<>(modelClass);
    }

    @Override
    @Transactional
    public List<Long> update(List<Field<?>> source) {
        return repository.update(providerSpecification(source));
    }


    @Override
    public SpecificationUpdate<MODEL> providerSpecification(List<Field<?>> fields) {

        return new UpdateCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map, map, map));
    }

}
