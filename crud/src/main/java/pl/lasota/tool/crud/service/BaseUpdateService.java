package pl.lasota.tool.crud.service;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.*;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.serach.field.Field;

import java.util.List;

@AllArgsConstructor
public abstract class BaseUpdateService<READING, MODEL extends EntityBase> implements UpdateService<READING> {

    private final UpdateRepository<MODEL> repository;
    private final Mapping<List<MODEL>, List<READING>> mapping;

    @Override
    @Transactional
    public List<READING> update(List<Field<?>> source) {
        List<MODEL> update = repository.update(providerSpecification(source));
        return mapping.mapper(update);
    }

    protected abstract SpecificationUpdate<MODEL> providerSpecification(List<Field<?>> fields);
}
