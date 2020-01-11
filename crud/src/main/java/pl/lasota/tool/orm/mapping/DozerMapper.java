package pl.lasota.tool.orm.mapping;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.io.InputStream;

/**
 * * Simple Implementation using Dozer Mapper to map between two different object or the same object
 *
 * @param <SOURCE>
 * @param <DESTINATION>
 */
public class DozerMapper<SOURCE, DESTINATION> implements Mapping<SOURCE, DESTINATION> {
    private final Class<DESTINATION> type;
    private final Mapper mapper;

    public DozerMapper(Class<DESTINATION> type, InputStream file) {
        this.type = type;
        mapper = DozerBeanMapperBuilder.create().withXmlMapping(() -> file).build();
    }

    public DozerMapper(Class<DESTINATION> type) {
        mapper = DozerBeanMapperBuilder.buildDefault();
        this.type = type;
    }


    @Override
    public DESTINATION mapper(SOURCE source) {
        return mapper.map(source, type);
    }
}
