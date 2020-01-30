package pl.lasota.tool.sr.dynamicrepository;

import pl.lasota.tool.sr.dynamicrepository.signaturesql.CreatableSql;

import javax.persistence.EntityManager;

public class ExecutorQuery {

    private final EntityManager entityManager;

    public ExecutorQuery(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void builder(CreatableSql CreatableSql) {
        String build = CreatableSql.create();
        System.out.println(build);
        entityManager.createNativeQuery(build).executeUpdate();
    }

}
