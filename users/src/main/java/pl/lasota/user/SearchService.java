package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.mapping.DozerPageMapping;
import pl.lasota.tool.sr.repository.search.SearchRepository;
import pl.lasota.tool.sr.service.base.BaseSearchService;

@Service
public class SearchService extends BaseSearchService<Car, Car> {
    public SearchService(SearchRepository<Car> repository) {
        super(repository, new DozerPageMapping<>(Car.class), Car.class);

    }
}
