package pl.lasota.tool.crud;

import org.dozer.DozerBeanMapper;
import pl.lasota.tool.crud.mapping.Mapping;

import java.util.ArrayList;
import java.util.List;

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
