package pl.lasota.database.repository.update;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.database.repository.update.specification.SpecificationUpdate;
import pl.lasota.database.repository.BasicEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
public class SimpleUpdateRepository<MODEL extends BasicEntity> implements UpdateRepository<MODEL> {

    private final EntityManager em;
    private Class<MODEL> modelClass;

    public SimpleUpdateRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public List<Long> update(SpecificationUpdate<MODEL> specification) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MODEL> query = cb.createQuery(modelClass);
        Root<MODEL> roo1t = query.from(modelClass);
        Predicate queryPredicate = specification.toPredicate(modelClass,roo1t, cb);
        query.where(queryPredicate);
        query.distinct(true);

        List<Long> collect = em.createQuery(query)
                .getResultList()
                .stream()
                .map(BasicEntity::getId)
                .collect(Collectors.toList());

        CriteriaUpdate<MODEL> update = cb.createCriteriaUpdate(modelClass);
        Root<MODEL> root = update.from(modelClass);

        Predicate predicateUpdate = specification.toPredicate(modelClass,root, update, cb);

        update.where(predicateUpdate);
        em.createQuery(update).executeUpdate();

        return collect;
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }
}
