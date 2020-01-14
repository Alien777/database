package pl.lasota.user;

import org.springframework.stereotype.Service;

import pl.lasota.tool.sr.repository.delete.DeleteRepository;
import pl.lasota.tool.sr.service.base.BaseDeleteService;

@Service
public class DeleteService extends BaseDeleteService<User> {

    public DeleteService(DeleteRepository<User> repository) {
        super(repository, User.class);
    }

}
