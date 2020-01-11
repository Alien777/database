package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.orm.mapping.DozerMapper;
import pl.lasota.tool.orm.repository.crud.CrudRepository;
import pl.lasota.tool.orm.service.base.BaseCrudService;
import pl.lasota.tool.orm.service.security.CrudSecurityDelegator;

@Service
public class CrudSecurityService extends CrudSecurityDelegator<UserDto, UserDto, UserDto, User> {

    public CrudSecurityService(CrudRepository<User> repository) {
        super(new BaseCrudService<>(repository,
                new DozerMapper<>(User.class),
                new DozerMapper<>(User.class),
                new DozerMapper<>(UserDto.class), User.class));
        repository.modelClass(User.class);
    }
}
