package pl.lasota.tool.sr.service.base;


import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.Specification;
import pl.lasota.tool.sr.repository.query.QueryUpdate;
import pl.lasota.tool.sr.repository.update.UpdateRepository;
import pl.lasota.tool.sr.repository.update.specification.SpecificationUpdate;
import pl.lasota.tool.sr.repository.update.specification.UpdateCriteriaSpecification;

import java.util.List;

public class UpdateAction<MODEL extends BasicEntity> implements Update, SpecificationProvider<Specification<MODEL>, QueryUpdate> {

    private final UpdateRepository<MODEL> repository;

    public UpdateAction(UpdateRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);

    }

    @Override
    @Transactional
    public List<Long> update(QueryUpdate queryUpdate) {
        return repository.update(providerSpecification(queryUpdate));
    }


    @Override
    public SpecificationUpdate<MODEL> providerSpecification(QueryUpdate fields) {
        return new UpdateCriteriaSpecification<>(fields, fields);
    }
}
