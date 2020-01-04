package pl.lasota.tool.crud.fulltextrepository;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.SimpleSearchRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public class FullTextSearchSimpleRepository<MODEL extends EntityBase> implements FullTextSearchRepository<MODEL> {

    private final FullTextEntityManager fullTextEntityManager;
    private Class<MODEL> modelClass;

    public FullTextSearchSimpleRepository(EntityManager em) {

        fullTextEntityManager = null;
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public Page<MODEL> find(SpecificationFullText specification, Pageable pageable) {

        fullTextEntityManager.getCriteriaBuilder();
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(modelClass).get();

        BooleanJunction<BooleanJunction> booleanJunction = queryBuilder.bool();
        specification.search(queryBuilder, booleanJunction);
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(booleanJunction.createQuery(), modelClass);

        SortContext sort = queryBuilder.sort();
        fullTextQuery.setSort(specification.sort(sort));

        List<MODEL> resultList = fullTextQuery
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
                .getResultList();

        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    @Override
    public void reindex(MassIndexerProgressMonitor progress) {


    }
}
