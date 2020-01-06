package pl.lasota.tool.crud.mapping;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple Implementation using Dozer Mapper to map one list to another list with different objects type
 *
 * @param <SOURCE>
 * @param <DESTINATION>
 */
public final class DozerListMapping<SOURCE, DESTINATION> implements Mapping<List<SOURCE>, List<DESTINATION>> {

    private final DozerMapper<SOURCE, DESTINATION> mapper;

    public DozerListMapping(Class<DESTINATION> destination, InputStream file) {
        mapper = new DozerMapper<>(destination, file);
    }

    public DozerListMapping(Class<DESTINATION> destination) {
        mapper = new DozerMapper<>(destination);
    }

    @Override
    public List<DESTINATION> mapper(List<SOURCE> sources) {
        return sources.stream().map(mapper::mapper).collect(Collectors.toList());
    }
}
