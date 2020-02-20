package com.tools.database;


import java.io.IOException;
import java.util.List;

public interface ServiceApi<OBJECT_DTO> {

    void create(OBJECT_DTO object);

    void update(OBJECT_DTO object, Long id);

    List<OBJECT_DTO> search(DefaultSearchProvider defaultSearchProvider);

    OBJECT_DTO get(Long id);

    void delete(Long id) throws IOException;
}
