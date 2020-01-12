package pl.lasota.tool.orm.service;

public interface CrudService<CREATING, READING, UPDATING> {

    READING save(CREATING CREATING);

    READING get(Long id);

    Long delete(Long id);

    READING update(UPDATING updating);
}