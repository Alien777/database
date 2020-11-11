package pl.lasota.database.repository;

public interface Repository<MODEL extends BasicEntity> {
    void modelClass(Class<MODEL> modelClass);
}
