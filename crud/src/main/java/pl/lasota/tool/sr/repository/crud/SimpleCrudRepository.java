package pl.lasota.tool.sr.repository.crud;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.repository.BasicEntity;

import javax.persistence.EntityManager;

@Transactional(readOnly = true)
public class SimpleCrudRepository<MODEL extends BasicEntity> implements CrudRepository<MODEL> {
    private final EntityManager em;
    private Class<MODEL> modelClass;

    public SimpleCrudRepository(EntityManager em) {
        this.em = em;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MODEL update(MODEL create) {
        if (create.getId() == null) {
            return null;
        }

        return em.merge(create);
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {

        this.modelClass = modelClass;
    }
}
