package pl.lasota.tool.orm.service.base;

import pl.lasota.tool.orm.field.Field;
import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Specification;
import pl.lasota.tool.orm.repository.mapping.FieldMapping;
import pl.lasota.tool.orm.repository.delete.DeleteRepository;
import pl.lasota.tool.orm.repository.delete.SpecificationDelete;
import pl.lasota.tool.orm.repository.delete.criteria.DeleteCriteriaSpecification;
import pl.lasota.tool.orm.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.orm.service.DeleteService;
import pl.lasota.tool.orm.service.SpecificationProvider;

import java.util.List;

public class BaseDeleteService<MODEL extends EntityBase> implements DeleteService, SpecificationProvider<Specification<MODEL>> {

    private final DeleteRepository<MODEL> repository;
    private final FieldMapping<MODEL> map;

    public BaseDeleteService(DeleteRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        map = new FieldMapping<>(modelClass);
    }

    @Override
    public List<Long> delete(List<Field<?>> source) {
        return repository.delete(providerSpecification(source));
    }

    @Override
    public SpecificationDelete<MODEL> providerSpecification(List<Field<?>> fields) {

        return new DeleteCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map, map, map));
    }

}
