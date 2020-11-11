package pl.lasota.database.service.base;

public interface Crud<CREATING, READING, UPDATING> {

    READING save(CREATING CREATING);

    READING get(Long id);

    Long delete(Long id);

    READING update(Long id, UPDATING updating);
}
