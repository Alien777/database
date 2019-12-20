package pl.lasota.tool.crud;

import org.springframework.data.domain.Page;
import pl.lasota.tool.crud.mapping.Mapping;

public final class DozerPageMapping<SOURCE, DESTINATION> implements Mapping<Page<SOURCE>, Page<DESTINATION>> {

    private final DozerMapper<SOURCE, DESTINATION> mapper;

    public DozerPageMapping(String file, Class<DESTINATION> destination) {
        mapper = new DozerMapper<>(destination, file);
    }

    public DozerPageMapping(Class<DESTINATION> destination) {
        mapper = new DozerMapper<>(destination);
    }

    @Override
    public Page<DESTINATION> mapper(Page<SOURCE> sources) {
        return sources.map(mapper::mapper);
    }
}
