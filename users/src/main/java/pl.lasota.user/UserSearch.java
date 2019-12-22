package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerPageMapping;
import pl.lasota.tool.crud.mapping.SketchSearchMapping;
import pl.lasota.tool.crud.repository.SearchRepository;
import pl.lasota.tool.crud.serach.criteria.FieldMapper;
import pl.lasota.tool.crud.service.CriteriaSearchService;

@Service
public class UserSearch extends CriteriaSearchService<UserDto, User> {

    public UserSearch(SearchRepository<User> repository) {
        super(repository, new SketchSearchMapping<>(new DozerPageMapping<>(UserDto.class)), new FieldMapper<>(),10);
    }
}
