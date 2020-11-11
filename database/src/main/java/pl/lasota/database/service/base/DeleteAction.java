package pl.lasota.database.service.base;

import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.Specification;
import pl.lasota.database.repository.delete.DeleteRepository;
import pl.lasota.database.repository.delete.specification.DeleteCriteriaSpecification;
import pl.lasota.database.repository.delete.specification.SpecificationDelete;
import pl.lasota.database.repository.query.QueryDelete;

import java.util.List;

public class DeleteAction<MODEL extends BasicEntity> implements Delete, SpecificationProvider<Specification<MODEL>, QueryDelete> {

    private final DeleteRepository<MODEL> repository;

    public DeleteAction(DeleteRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
    }

    @Override
    public List<Long> delete(QueryDelete queryDelete) {
        return repository.delete(providerSpecification(queryDelete));
    }

    @Override
    public SpecificationDelete<MODEL> providerSpecification(QueryDelete queryDelete) {
        return new DeleteCriteriaSpecification<>(queryDelete);
    }

}
