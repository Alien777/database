package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.*;
import pl.lasota.tool.crud.mapping.SimpleSearchMapping;
import pl.lasota.tool.crud.repository.SearchRepository;

@Service
public class UserSearch extends MapperSearchService<User, User, Long> {

    public UserSearch(SearchRepository<User, Long> repository) {
        super(repository, new SimpleSearchMapping<>(
                new DozerListMapping<>(User.class),
                new DozerPageMapping<>(User.class)));
    }
}
