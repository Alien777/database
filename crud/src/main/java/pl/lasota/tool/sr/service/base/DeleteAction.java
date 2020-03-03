package pl.lasota.tool.sr.service.base;

import pl.lasota.tool.sr.field.definition.Field;
import pl.lasota.tool.sr.repository.CriteriaFieldMapping;
import pl.lasota.tool.sr.field.DistributeForField;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.Specification;
import pl.lasota.tool.sr.repository.delete.DeleteRepository;
import pl.lasota.tool.sr.repository.delete.specification.DeleteCriteriaSpecification;
import pl.lasota.tool.sr.repository.delete.specification.SpecificationDelete;

import java.util.List;

public class DeleteAction<MODEL extends EntityBase> implements Delete, SpecificationProvider<Specification<MODEL>> {

    private final DeleteRepository<MODEL> repository;
    private final CriteriaFieldMapping<MODEL> map;

    public DeleteAction(DeleteRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        map = new CriteriaFieldMapping<>(modelClass);
    }

    @Override
    public List<Long> delete(List<Field<?>> source) {
        return repository.delete(providerSpecification(source));
    }

    @Override
    public SpecificationDelete<MODEL> providerSpecification(List<Field<?>> fields) {
        return new DeleteCriteriaSpecification<>(new DistributeForField(filter(fields)), map);
    }

}
