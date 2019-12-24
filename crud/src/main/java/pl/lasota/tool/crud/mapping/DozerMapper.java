package pl.lasota.tool.crud.mapping;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

/**
 *  * Simple Implementation using Dozer Mapper to map between two different object or the same object
 * @param <SOURCE>
 * @param <DESTINATION>
 */
public class DozerMapper<SOURCE, DESTINATION> implements Mapping<SOURCE, DESTINATION> {
    private final Class<DESTINATION> type;
    private final DozerBeanMapper mapper = new DozerBeanMapper();

    public DozerMapper(Class<DESTINATION> type, String file) {
        this.type = type;
        List<String> myMappingFiles = new ArrayList<>();
        myMappingFiles.add(file);
        mapper.setMappingFiles(myMappingFiles);
    }

    public DozerMapper(Class<DESTINATION> type) {
        this.type = type;
    }


    @Override
    public DESTINATION mapper(SOURCE source) {
        return mapper.map(source, type);
    }
}
