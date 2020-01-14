package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.repository.crud.CrudRepository;
import pl.lasota.tool.sr.service.base.BaseCrudService;

@Service
public class CrudService extends BaseCrudService<User, User, UserDto, User> {

    public CrudService(CrudRepository<User> repository) {
        super(repository, new DozerMapper<>(User.class), new DozerMapper<>(User.class), new DozerMapper<>(User.class), User.class);
    }
}
