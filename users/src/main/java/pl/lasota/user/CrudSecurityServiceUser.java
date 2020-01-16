package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.repository.crud.CrudRepository;
import pl.lasota.tool.sr.service.base.BaseCrudService;
import pl.lasota.tool.sr.service.security.CrudSecurityDelegator;
import pl.lasota.tool.sr.service.security.ProvidingRules;

@Service
public class CrudSecurityServiceUser extends CrudSecurityDelegator<UserDto, UserDto, UserDto, User> {

    public CrudSecurityServiceUser(CrudRepository<User> repository, ProvidingRules providingRules) {
        super(new BaseCrudService<>(repository,
                new DozerMapper<>(User.class),
                new DozerMapper<>(User.class),
                new DozerMapper<>(UserDto.class), User.class), providingRules);
        repository.modelClass(User.class);
    }
}
