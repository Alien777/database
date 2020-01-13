package pl.lasota.tool.orm.repository.search;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.orm.common.EntityBase;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional(readOnly = true)
@Repository
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

        //todo not working count available
//        CriteriaQuery<MODEL> query1 = cb.createQuery(modelClass);
//        Root<MODEL> root1 = query1.from(modelClass);
//        Predicate predicate1 = specification.toPredicate(root1, query1, cb);
//        CriteriaBuilder qb = em.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
//        cq.select(qb.count(cq.from(modelClass)));
//        cq.where(predicate1);
//        query1.distinct(true);
//        Long singleResult = em.createQuery(cq).getSingleResult();
        return new PageImpl<>(resultList, pageable, 1);
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }
}
