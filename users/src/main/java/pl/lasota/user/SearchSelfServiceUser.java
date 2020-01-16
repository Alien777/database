package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.mapping.DozerPageMapping;
import pl.lasota.tool.sr.repository.search.SearchRepository;
import pl.lasota.tool.sr.service.base.BaseSearchService;
import pl.lasota.tool.sr.service.security.ProvidingRules;
import pl.lasota.tool.sr.service.security.SearchSecurityDelegator;

@Service
public class SearchSelfServiceUser extends SearchSecurityDelegator<UserDto, User> {

    public SearchSelfServiceUser(SearchRepository<User> repository, ProvidingRules providingRules) {
        super(new BaseSearchService<>(repository, new DozerPageMapping<>(UserDto.class), User.class), providingRules);

    }
}
