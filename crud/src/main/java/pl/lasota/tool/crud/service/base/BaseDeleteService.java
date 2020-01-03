package pl.lasota.tool.crud.service.base;

import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.mapping.FieldMapping;
import pl.lasota.tool.crud.repository.delete.DeleteRepository;
import pl.lasota.tool.crud.repository.delete.SpecificationDelete;
import pl.lasota.tool.crud.repository.delete.criteria.DeleteCriteriaSpecification;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.service.DeleteService;

import java.util.List;

public class BaseDeleteService<MODEL extends EntityBase> implements DeleteService<MODEL> {

    private final DeleteRepository<MODEL> repository;

    public BaseDeleteService(DeleteRepository<MODEL> repository) {
        this.repository = repository;
    }

    @Override
    public List<Long> delete(List<Field<?>> source) {
        return repository.delete(providerSpecification(source));
    }

    @Override
    public SpecificationDelete<MODEL> providerSpecification(List<Field<?>> fields) {
        FieldMapping<MODEL> map = new FieldMapping<>();
        return new DeleteCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map, map, map));
    }

}
