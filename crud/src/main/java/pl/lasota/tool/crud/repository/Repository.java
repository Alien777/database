package pl.lasota.tool.crud.repository;

import pl.lasota.tool.crud.common.EntityBase;

public interface Repository<MODEL extends EntityBase> {
    void modelClass(Class<MODEL> modelClass);
}
