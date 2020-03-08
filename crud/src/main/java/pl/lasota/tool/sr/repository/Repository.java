package pl.lasota.tool.sr.repository;

public interface Repository<MODEL extends BasicEntity> {
    void modelClass(Class<MODEL> modelClass);
}
