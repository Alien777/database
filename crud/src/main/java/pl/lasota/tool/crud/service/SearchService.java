package pl.lasota.tool.crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService<READING, SOURCE> {

    Page<READING> find(SOURCE source, Pageable pageable);

    Page<READING> find(SOURCE source);

    long count(SOURCE source);
}
