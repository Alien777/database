package pl.lasota.tool.orm.service.base;


import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.orm.field.Field;
import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Specification;
import pl.lasota.tool.orm.repository.CriteriaFieldMapping;
import pl.lasota.tool.orm.repository.DistributeCriteriaFactory;
import pl.lasota.tool.orm.repository.update.SpecificationUpdate;
import pl.lasota.tool.orm.repository.update.UpdateRepository;
import pl.lasota.tool.orm.repository.update.criteria.UpdateCriteriaSpecification;
import pl.lasota.tool.orm.service.SpecificationProvider;
import pl.lasota.tool.orm.service.UpdateService;

import java.util.List;

public class BaseUpdateService<MODEL extends EntityBase> implements UpdateService, SpecificationProvider<Specification<MODEL>> {

    private final UpdateRepository<MODEL> repository;

    private final CriteriaFieldMapping<MODEL> map;

    public BaseUpdateService(UpdateRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        map = new CriteriaFieldMapping<>(modelClass);
    }

    @Override
    @Transactional
    public List<Long> update(List<Field<?>> source) {
        return repository.update(providerSpecification(source));
    }


    @Override
    public SpecificationUpdate<MODEL> providerSpecification(List<Field<?>> fields) {

        return new UpdateCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map));
    }

}
