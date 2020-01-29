package pl.lasota.tool.sr.dynamicrepository;

import javax.persistence.EntityManager;

public class ExecutorQuery {

    private final EntityManager entityManager;

    public ExecutorQuery(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void builder(Buildable buildable) {
        String build = buildable.build();
        System.out.println(build);
        entityManager.createNativeQuery(build).executeUpdate();
    }

}
