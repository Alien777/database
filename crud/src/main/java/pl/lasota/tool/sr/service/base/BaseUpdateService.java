package pl.lasota.tool.sr.service.base;


import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.repository.CriteriaFieldMapping;
import pl.lasota.tool.sr.field.DistributeForField;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.Specification;
import pl.lasota.tool.sr.repository.update.UpdateRepository;
import pl.lasota.tool.sr.repository.update.specification.SpecificationUpdate;
import pl.lasota.tool.sr.repository.update.specification.UpdateCriteriaSpecification;
import pl.lasota.tool.sr.service.SpecificationProvider;
import pl.lasota.tool.sr.service.UpdateService;

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
        return new UpdateCriteriaSpecification<>(new DistributeForField(filter(fields)), map);
    }
}
