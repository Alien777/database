package pl.lasota.tool.crud.repository.update;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.repository.EntityBase;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;


@Transactional(readOnly = true)
public class UpdateFieldRepository<MODEL extends EntityBase> implements UpdateRepository<MODEL> {

    private final EntityManager em;
    private final Class<MODEL> modelClass;

    public UpdateFieldRepository(EntityManager em, Class<MODEL> modelClass) {
        this.em = em;
        this.modelClass = modelClass;
    }

    @Override
    @Transactional
    public List<MODEL> update(SpecificationUpdate<MODEL> specification) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<MODEL> update = cb.createCriteriaUpdate(modelClass);
        Root<MODEL> root = update.from(modelClass);

        Predicate predicateUpdate = specification.toPredicate(root, update, cb);

        update.where(predicateUpdate);
        em.createQuery(update).executeUpdate();

        CriteriaQuery<MODEL> query = cb.createQuery(modelClass);
        Root<MODEL> roo1t = query.from(modelClass);
        Predicate queryPredicate = specification.toPredicate(roo1t, query, cb);
        query.where(queryPredicate);
        return em.createQuery(query).getResultList();

    }
}
