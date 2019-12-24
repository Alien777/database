package pl.lasota.tool.crud.repository;

public interface Repository<MODEL> {
    void modelClass(Class<MODEL> modelClass);
}
