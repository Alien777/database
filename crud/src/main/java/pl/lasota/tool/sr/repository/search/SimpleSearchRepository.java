package pl.lasota.tool.sr.repository.search;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.search.specification.SpecificationQuery;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional(readOnly = true)
public class SimpleSearchRepository<MODEL extends EntityBase> implements SearchRepository<MODEL> {

    private final EntityManager em;
    protected Class<MODEL> modelClass;


    public SimpleSearchRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MODEL> query = cb.createQuery(modelClass);
        Root<MODEL> root = query.from(modelClass);

        Predicate predicate = specification.toPredicate(root, query, cb);

        query.where(predicate);
        query.distinct(true);
        List<MODEL> resultList = em.createQuery(query)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
                .getResultList();


        CriteriaBuilder qb1 = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq1 = qb1.createQuery(Long.class);
        cq1.select(qb1.count(cq1.from(modelClass)));
        Predicate predicate1 = specification.toPredicate(root, query, cb);
        cq1.where(predicate1);
        cq1.distinct(true);
        Long singleResult = em.createQuery(cq1).getSingleResult();

        return new PageImpl<>(resultList, pageable, singleResult);
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }
}
