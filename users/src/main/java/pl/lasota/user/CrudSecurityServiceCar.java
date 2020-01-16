package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.repository.crud.CrudRepository;
import pl.lasota.tool.sr.service.base.BaseCrudService;
import pl.lasota.tool.sr.service.security.CrudSecurityDelegator;

@Service
public class CrudSecurityServiceCar extends CrudSecurityDelegator<Car, Car, Car, Car> {

    public CrudSecurityServiceCar(CrudRepository<Car> repository) {
        super(new BaseCrudService<>(repository,
                new DozerMapper<>(Car.class),
                new DozerMapper<>(Car.class),
                new DozerMapper<>(Car.class), Car.class), providingRules);
        repository.modelClass(Car.class);
    }
}
