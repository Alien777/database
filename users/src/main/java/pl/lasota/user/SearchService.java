package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.mapping.DozerPageMapping;
import pl.lasota.tool.sr.repository.search.SearchRepository;
import pl.lasota.tool.sr.service.base.BaseSearchService;

@Service
public class SearchService extends BaseSearchService<User, User> {
    public SearchService(SearchRepository<User> repository) {
        super(repository, new DozerPageMapping<>(User.class), User.class);

    }
}
