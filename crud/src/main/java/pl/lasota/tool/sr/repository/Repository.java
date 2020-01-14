package pl.lasota.tool.sr.repository;

public interface Repository<MODEL extends EntityBase> {
    void modelClass(Class<MODEL> modelClass);
}
