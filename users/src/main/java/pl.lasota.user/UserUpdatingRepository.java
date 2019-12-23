package pl.lasota.user;

import org.springframework.stereotype.Repository;
import pl.lasota.tool.crud.repository.SearchRepository;

@Repository
interface UserUpdatingRepository extends SearchRepository<User> {
}