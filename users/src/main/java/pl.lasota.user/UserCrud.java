package pl.lasota.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.DozerMapper;
import pl.lasota.tool.crud.DozerPageMapping;
import pl.lasota.tool.crud.mapping.SimpleCrudMapping;
import pl.lasota.tool.crud.repository.CrudRepository;
import pl.lasota.tool.crud.MapperCrudService;

@Service
public class UserCrud extends MapperCrudService<User, User, User, User, Long> {

    @Autowired
    public UserCrud(CrudRepository<User,Long> crudRepository) {
        super(crudRepository, new SimpleCrudMapping<>(
                new DozerMapper<>(User.class),
                new DozerMapper<>(User.class),
                new DozerMapper<>(User.class),
                new DozerPageMapping<>(User.class)));
    }
}
