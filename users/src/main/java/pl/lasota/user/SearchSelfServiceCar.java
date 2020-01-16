package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.sr.mapping.DozerPageMapping;
import pl.lasota.tool.sr.repository.search.SearchRepository;
import pl.lasota.tool.sr.service.base.BaseSearchService;
import pl.lasota.tool.sr.service.security.SearchSecurityDelegator;

@Service
public class SearchSelfServiceCar extends SearchSecurityDelegator<Car, Car> {
    public SearchSelfServiceCar(SearchRepository<Car> repository) {
        super(new BaseSearchService<>(repository, new DozerPageMapping<>(Car.class), Car.class), providingRules);

    }
}
