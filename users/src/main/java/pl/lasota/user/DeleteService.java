package pl.lasota.user.service;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.*;

import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.service.BaseUpdateService;
import pl.lasota.user.User;

@Service
public class D extends BaseUpdateService<User, User> {

    public UpdateService(UpdateRepository<User> repository) {
        super(repository, new DozerListMapping<>(User.class), User.class);
    }


}
