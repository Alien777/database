package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.FieldMapperFields;
import pl.lasota.tool.crud.repository.delete.DeleteRepository;
import pl.lasota.tool.crud.repository.delete.SpecificationDelete;
import pl.lasota.tool.crud.repository.delete.criteria.DeleteCriteriaSpecification;
import pl.lasota.tool.crud.repository.distributed.DistributeFactory;

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
        return new DeleteCriteriaSpecification<>(new DistributeFactory<>(filter(fields), new FieldMapperFields<>()));
    }

}
