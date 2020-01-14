package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.repository.tokensearch.TokenSearchRepository;
import pl.lasota.tool.sr.mapping.DozerPageMapping;
import pl.lasota.tool.sr.service.base.BaseFullTextSearchService;

@Service
public class FullSearchService extends BaseFullTextSearchService<User, User> {
    public FullSearchService(TokenSearchRepository<User> repository) {
        super(repository, new DozerPageMapping<>(User.class));
    }
}
