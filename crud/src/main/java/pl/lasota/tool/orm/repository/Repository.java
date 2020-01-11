package pl.lasota.tool.orm.repository;

import pl.lasota.tool.orm.common.EntityBase;

public interface Repository<MODEL extends EntityBase> {
    void modelClass(Class<MODEL> modelClass);
}
