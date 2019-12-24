package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerPageMapping;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.service.BaseSearchService;

@Service
public class SearchService extends BaseSearchService<User, User> {

    public SearchService(SearchRepository<User> repository) {
        super(repository, new DozerPageMapping<>(User.class));
        repository.modelClass(User.class);
    }
}
