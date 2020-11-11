package pl.lasota.database.repository.search;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.search.specification.SpecificationQuery;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional(readOnly = true)
public class SimpleSearchRepository<MODEL extends BasicEntity> implements SearchRepository<MODEL> {

    private final EntityManager em;
    protected Class<MODEL> modelClass;


    public SimpleSearchRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable, boolean count) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MODEL> query = cb.createQuery(modelClass);
        Root<MODEL> root = query.from(modelClass);

        Predicate predicate = specification.toPredicate(modelClass, root, query, cb);

        query.where(predicate);
        query.distinct(true);
        List<MODEL> resultList = em.createQuery(query)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
                .getResultList();

        if (count) {
            CriteriaBuilder qb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = qb.createQuery(Long.class);
            cq.select(qb.count(cq.from(modelClass)));
            cq.where(predicate);
            Long singleResult = em.createQuery(cq).getSingleResult();
            return new PageImpl<>(resultList, pageable, singleResult);
        }
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }
}
