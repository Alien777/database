package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerListMapping;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.service.BaseUpdateService;

@Service
public class UpdateService extends BaseUpdateService<User, User> {

    public UpdateService(UpdateRepository<User> repository) {
        super(repository, new DozerListMapping<>(User.class), User.class);
        repository.modelClass(User.class);
    }
}
