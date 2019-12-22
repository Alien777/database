package pl.lasota.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerMapper;
import pl.lasota.tool.crud.mapping.SketchCrudMapping;
import pl.lasota.tool.crud.repository.CrudRepository;
import pl.lasota.tool.crud.repository.SimpleCrudRepository;
import pl.lasota.tool.crud.service.BaseCrudService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserCrud extends BaseCrudService<User, User, User, User> {

    public UserCrud(EntityManager em) {
        super(new SimpleCrudRepository<>(em, User.class), new SketchCrudMapping<>(
                new DozerMapper<>(User.class),
                new DozerMapper<>(User.class),
                new DozerMapper<>(User.class)));
    }
}
