package pl.lasota.tool.crud.mapping;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * @param <READING>
 *
 * @param <MODEL>
 */
@AllArgsConstructor
public class SketchSearchMapping<READING, MODEL> implements SearchMapping<READING, MODEL> {

    private final Mapping<Page<MODEL>, Page<READING>> pageMapping;

    @Override
    public Page<READING> mapping(Page<MODEL> model) {
        return pageMapping.mapper(model);
    }

}
