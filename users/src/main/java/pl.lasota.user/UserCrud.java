package pl.lasota.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerMapper;
import pl.lasota.tool.crud.mapping.SketchCrudMapping;
import pl.lasota.tool.crud.repository.CrudRepository;
import pl.lasota.tool.crud.service.BaseCrudService;

@Service
public class UserCrud extends BaseCrudService<User, User, User, User> {

    @Autowired
    public UserCrud(CrudRepository<User> crudRepository) {
        super(crudRepository, new SketchCrudMapping<>(
                new DozerMapper<>(User.class),
                new DozerMapper<>(User.class),
                new DozerMapper<>(User.class)));
    }
}
