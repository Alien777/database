package pl.lasota.tool.crud.repository.search;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.repository.EntityBase;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Transactional(readOnly = true)
public class SimpleSearchRepository<MODEL extends EntityBase> implements SearchRepository<MODEL> {

    private final EntityManager em;
    private final Class<MODEL> modelClass;

    public SimpleSearchRepository(EntityManager em, Class<MODEL> modelClass) {
        this.em = em;
        this.modelClass = modelClass;
    }


    @Override
    public Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MODEL> query = cb.createQuery(modelClass);
        Root<MODEL> root = query.from(modelClass);

        Predicate predicate1 = specification.toPredicate(root, query, cb);
        query.where(predicate1);
        List<MODEL> resultList = em.createQuery(query)
                .setMaxResults(pageable.getPageSize() * (pageable.getPageNumber() + 1))
                .setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
                .getResultList();

        return new PageImpl<>(resultList, pageable, 100);
    }
}
