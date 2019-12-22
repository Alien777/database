package pl.lasota.tool.crud.repository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;


@Transactional(readOnly = true)
public class UpdateFieldRepository<MODEL extends EntityBase> extends SimpleCrudRepository<MODEL>
        implements UpdateRepository<MODEL> {

    private final EntityManager em;
    private final Class<MODEL> modelClass;

    public UpdateFieldRepository(EntityManager em, Class<MODEL> modelClass) {
        super(em, modelClass);
        this.em = em;
        this.modelClass = modelClass;
    }

    @Override
    @Transactional
    public List<MODEL> update(Specification<MODEL> specification) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<MODEL> update = cb.createCriteriaUpdate(modelClass);
        Root<MODEL> root = update.from(modelClass);

        Predicate predicate = specification.toPredicate(root, update, cb);
        update.where(predicate);
        em.createQuery(update).executeUpdate();

        CriteriaQuery<MODEL> query = cb.createQuery(modelClass);

        return em.createQuery(query).getResultList();
    }
}
