package pl.lasota.tool.crud.service;

import lombok.AllArgsConstructor;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.FieldMapperFields;
import pl.lasota.tool.crud.repository.delete.DeleteRepository;
import pl.lasota.tool.crud.repository.delete.SpecificationDelete;
import pl.lasota.tool.crud.repository.delete.criteria.DeleteCriteriaSpecification;
import pl.lasota.tool.crud.repository.distributed.DistributeFactory;

import java.util.List;


@AllArgsConstructor
public class BaseDeleteService<MODEL extends EntityBase> implements DeleteService<MODEL> {

    private final DeleteRepository<MODEL> repository;
    private final Class<MODEL> modelClass;

    @Override
    public List<Long> delete(List<Field<?>> source) {
        return repository.delete(providerSpecification(source));
    }

    @Override
    public SpecificationDelete<MODEL> providerSpecification(List<Field<?>> fields) {
        return new DeleteCriteriaSpecification<>(new DistributeFactory<>(filter(fields), new FieldMapperFields<>()));
    }

}
