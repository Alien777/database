package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.orm.mapping.DozerPageMapping;
import pl.lasota.tool.orm.repository.search.SearchRepository;
import pl.lasota.tool.orm.service.base.BaseSearchService;

@Service
public class SearchService extends BaseSearchService<User, User> {
    public SearchService(SearchRepository<User> repository) {
        super(repository, new DozerPageMapping<>(User.class), User.class);

    }
}
