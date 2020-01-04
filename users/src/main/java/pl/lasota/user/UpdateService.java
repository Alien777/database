package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.service.base.BaseUpdateService;

@Service
public class UpdateService extends BaseUpdateService<User> {

    public UpdateService(UpdateRepository<User> repository) {
        super(repository);
        repository.modelClass(User.class);
    }
}
