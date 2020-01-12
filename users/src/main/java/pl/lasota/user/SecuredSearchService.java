package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.orm.mapping.DozerMapper;
import pl.lasota.tool.orm.mapping.DozerPageMapping;
import pl.lasota.tool.orm.repository.crud.CrudRepository;
import pl.lasota.tool.orm.repository.search.SearchRepository;
import pl.lasota.tool.orm.service.SearchService;
import pl.lasota.tool.orm.service.base.BaseCrudService;
import pl.lasota.tool.orm.service.base.BaseSearchService;
import pl.lasota.tool.orm.service.security.CrudSecurityDelegator;
import pl.lasota.tool.orm.service.security.SearchSecurityDelegator;

@Service
public class SecuredSearchService extends SearchSecurityDelegator<UserDto, User> {


    public SecuredSearchService(SearchRepository<User> repository) {
        super(new BaseSearchService<>(repository, new DozerPageMapping<>(UserDto.class), User.class)
        );
    }


}
