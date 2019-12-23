package pl.lasota.tool.crud.repository.crud;


import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.repository.EntityBase;

import javax.persistence.EntityManager;

@Transactional(readOnly = true)
public class SimpleCrudRepository<MODEL extends EntityBase> implements CrudRepository<MODEL> {
    private final EntityManager em;
    private final Class<MODEL> modelClass;

    public SimpleCrudRepository(EntityManager em, Class<MODEL> modelClass) {
        this.em = em;
        this.modelClass = modelClass;
    }

    @Override
    @Transactional
    public MODEL save(MODEL create) {
        if (create.getId() != null) {
            return null;
        }
        em.persist(create);
        return create;
    }

    @Override
    public MODEL get(Long id) {
        MODEL model = em.find(modelClass, id);
        if (model == null) {
            return null;
        }

        return em.getReference(modelClass, id);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        MODEL model = em.find(modelClass, id);
        em.remove(em.contains(model) ? model : em.merge(model));
        return id;
    }

    @Override
    public MODEL update(MODEL create) {
        if (create.getId() == null) {
            return null;
        }
        MODEL model = em.find(modelClass, create.getId());
        if (model == null) {
            return null;
        }
        return em.merge(create);
    }
}
