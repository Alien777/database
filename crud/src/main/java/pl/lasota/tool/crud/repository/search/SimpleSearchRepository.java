package pl.lasota.tool.crud.repository.search;


import com.google.common.reflect.TypeToken;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.delete.SimpleDeleteRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public class SimpleSearchRepository<MODEL extends EntityBase> implements SearchRepository<MODEL> {

    private final EntityManager em;
    private Class<MODEL> modelClass;


    public SimpleSearchRepository(EntityManager em) {
        this.em = em;
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

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }
}
