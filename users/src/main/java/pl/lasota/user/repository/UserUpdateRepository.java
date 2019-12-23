package pl.lasota.user.repository;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerListMapping;
import pl.lasota.tool.crud.repository.search.criteria.DistributeFieldFactory;
import pl.lasota.tool.crud.repository.search.criteria.FieldMapperFields;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.repository.update.criteria.UpdateCriteriaSpecification;
import pl.lasota.tool.crud.serach.field.Field;
import pl.lasota.tool.crud.service.BaseUpdateService;
import pl.lasota.user.User;

import java.util.List;

@Service
public class UpdateService extends BaseUpdateService<User, User> {

    public UpdateService(UpdateRepository<User> repository) {
        super(repository, new DozerListMapping<>(User.class));
    }

    @Override
    protected SpecificationUpdate<User> providerSpecification(List<Field<?>> fields) {
        return new UpdateCriteriaSpecification<>(new DistributeFieldFactory<>(fields, new FieldMapperFields<>()));
    }
}
