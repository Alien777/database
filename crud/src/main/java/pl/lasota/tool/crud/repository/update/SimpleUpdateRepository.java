package pl.lasota.tool.crud.repository.update;

import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.repository.EntityBase;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;


@Transactional(readOnly = true)
@Repository
public class SimpleUpdateRepository<MODEL extends EntityBase> implements UpdateRepository<MODEL> {

    private final EntityManager em;
    private Class<MODEL> modelClass;

    public SimpleUpdateRepository(EntityManager em) {
        ResolvableType generic = ResolvableType.forClass(this.getClass()).getGeneric(0);
        modelClass = (Class<MODEL>) generic.getRawClass();

        this.em = em;
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

    @Override
    public void modelClass(Class<MODEL> modelClass) {

        this.modelClass = modelClass;
    }
}
