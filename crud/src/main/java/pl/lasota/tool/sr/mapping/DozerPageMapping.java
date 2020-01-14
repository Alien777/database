package pl.lasota.tool.sr.mapping;

import org.springframework.data.domain.Page;

import java.io.InputStream;

/**
 *  * Simple Implementation using Dozer Mapper to map one spring page to another spring page with different objects type
 * @param <SOURCE>
 * @param <DESTINATION>
 */
public final class DozerPageMapping<SOURCE, DESTINATION> implements Mapping<Page<SOURCE>, Page<DESTINATION>> {

    private final DozerMapper<SOURCE,DESTINATION> mapper;


    public DozerPageMapping(InputStream file, Class<DESTINATION> destination) {
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
