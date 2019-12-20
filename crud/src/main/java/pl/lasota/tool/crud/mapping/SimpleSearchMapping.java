package pl.lasota.tool.crud.mapping;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@AllArgsConstructor
public class SimpleSearchMapping<READ, MODEL> implements SearchMapping<READ, MODEL> {

    private final Mapping<List<MODEL>, List<READ>> listMapping;

    private final Mapping<Page<MODEL>, Page<READ>> pageMapping;

    @Override
    public Page<READ> mapping(Page<MODEL> model) {
        return pageMapping.mapper(model);
    }

    @Override
    public List<READ> mapping(List<MODEL> model) {
        return listMapping.mapper(model);
    }
}
