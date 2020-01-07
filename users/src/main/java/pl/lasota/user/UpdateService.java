package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.service.base.BaseUpdateService;
import pl.lasota.tool.crud.service.base.DynamicUpdateService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class UpdateService extends DynamicUpdateService<User> {

    public UpdateService(EntityManager entityManagerFactory) {
        super(entityManagerFactory, User.class);
    }
}
