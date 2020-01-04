package pl.lasota.tool.crud.service.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.service.SearchService;

import java.util.List;

public class SearchSecurityDelegator<READING> implements SearchService<READING> {

    private SearchService<READING> searchService;

    public SearchSecurityDelegator(SearchService<READING> searchService) {
        this.searchService = searchService;
    }

    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        return searchService.find(source, pageable);
    }

    @Override
    public Page<READING> find(List<Field<?>> source) {

        return searchService.find(source);

    }
}
