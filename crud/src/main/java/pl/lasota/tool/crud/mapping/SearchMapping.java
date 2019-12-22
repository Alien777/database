package pl.lasota.tool.crud.mapping;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Provide behavior mapping for search type service
 *
 * @param <READING>
 * @param <MODEL>
 */
public interface SearchMapping<READING, MODEL> {
    Page<READING> mapping(Page<MODEL> model);
}
