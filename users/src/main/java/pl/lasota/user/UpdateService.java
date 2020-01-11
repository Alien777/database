package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.orm.repository.update.UpdateRepository;
import pl.lasota.tool.orm.service.base.BaseUpdateService;

@Service
public class UpdateService extends BaseUpdateService<User> {

    public UpdateService(UpdateRepository<User> userUpdateRepository) {
        super(userUpdateRepository, User.class);
    }
}
