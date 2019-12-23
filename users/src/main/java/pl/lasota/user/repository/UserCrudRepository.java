package pl.lasota.user.repository;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerMapper;
import pl.lasota.tool.crud.repository.crud.CrudRepository;
import pl.lasota.tool.crud.service.BaseCrudService;
import pl.lasota.user.User;

@Service
public class CrudService extends BaseCrudService<User, User, User, User> {

    public CrudService(CrudRepository<User> repository) {
        super(repository, new DozerMapper<>(User.class), new DozerMapper<>(User.class), new DozerMapper<>(User.class));
    }
}
