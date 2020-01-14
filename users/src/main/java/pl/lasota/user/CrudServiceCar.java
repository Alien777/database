package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.repository.crud.CrudRepository;
import pl.lasota.tool.sr.service.base.BaseCrudService;

@Service
public class CrudServiceCar extends BaseCrudService<Car, Car, UserDto, Car> {

    public CrudServiceCar(CrudRepository<Car> repository) {
        super(repository, new DozerMapper<>(Car.class), new DozerMapper<>(Car.class), new DozerMapper<>(Car.class), Car.class);
    }
}
