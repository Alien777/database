package pl.lasota.tool.crud.service;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.*;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.serach.field.Field;

import java.util.List;

@AllArgsConstructor
public class BaseUpdateService<READING, MODEL extends EntityBase> implements UpdateService<READING> {

    private final UpdateRepository<MODEL> repository;
    private final Mapping<List<MODEL>, List<READING>> mapping;

    @Override
    public List<READING> update(List<Field<?>> source) {
        return null;
    }
}
