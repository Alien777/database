package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.*;

import pl.lasota.tool.crud.repository.delete.DeleteRepository;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.service.BaseDeleteService;
import pl.lasota.tool.crud.service.BaseUpdateService;
import pl.lasota.user.User;

@Service
public class DeleteService extends BaseDeleteService<User> {

    public DeleteService(DeleteRepository<User> repository) {
        super(repository);
        repository.modelClass(User.class);
    }

}
