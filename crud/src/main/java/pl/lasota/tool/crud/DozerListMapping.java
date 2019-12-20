package pl.lasota.tool.crud;

import org.springframework.data.domain.Page;
import pl.lasota.tool.crud.mapping.Mapping;

import java.util.List;
import java.util.stream.Collectors;

public final class DozerListMapping<SOURCE, DESTINATION> implements Mapping<List<SOURCE>, List<DESTINATION>> {

    private final DozerMapper<SOURCE, DESTINATION> mapper;

    public DozerListMapping(String file, Class<DESTINATION> destination) {
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
