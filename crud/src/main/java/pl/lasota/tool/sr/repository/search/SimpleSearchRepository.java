package pl.lasota.tool.sr.repository.search;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.search.specification.SpecificationQuery;

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
    public Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable) {

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



        return new PageImpl<>(resultList, pageable, resultList.size()+1);
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }
}
