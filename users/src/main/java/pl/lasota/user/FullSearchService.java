package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.orm.fulltextrepository.FullTextSearchRepository;
import pl.lasota.tool.orm.mapping.DozerPageMapping;
import pl.lasota.tool.orm.repository.search.SearchRepository;
import pl.lasota.tool.orm.service.base.BaseFullTextSearchService;
import pl.lasota.tool.orm.service.base.BaseSearchService;

@Service
public class FullSearchService extends BaseFullTextSearchService<User, User> {
    public FullSearchService(FullTextSearchRepository<User> repository) {
        super(repository, new DozerPageMapping<>(User.class));
    }
}
