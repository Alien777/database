package pl.lasota.user;

import org.springframework.stereotype.Repository;
import pl.lasota.tool.crud.repository.CrudRepository;

@Repository
interface CrudUserRepository extends CrudRepository<User, Long> {}