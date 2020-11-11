package pl.lasota.database.repository.delete;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.database.repository.delete.specification.SpecificationDelete;
import pl.lasota.database.repository.BasicEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
public class SimpleDeleteRepository<MODEL extends BasicEntity> implements DeleteRepository<MODEL> {

    private final EntityManager em;
    private Class<MODEL> modelClass;

    public SimpleDeleteRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public List<Long> delete(SpecificationDelete<MODEL> specification) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MODEL> query = cb.createQuery(modelClass);
        Root<MODEL> roo1t = query.from(modelClass);
        Predicate queryPredicate = specification.toPredicate(modelClass, roo1t, cb);
        query.where(queryPredicate);
        query.distinct(true);
        List<MODEL> resultList = em.createQuery(query).getResultList();

        CriteriaDelete<MODEL> criteriaDelete = cb.createCriteriaDelete(modelClass);
        Root<MODEL> root = criteriaDelete.from(modelClass);

        Predicate predicateUpdate = specification.toPredicate(modelClass, root, cb);

        criteriaDelete.where(predicateUpdate);
        em.createQuery(criteriaDelete).executeUpdate();

        return resultList.stream().map(BasicEntity::getId).collect(Collectors.toList());
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }
}
