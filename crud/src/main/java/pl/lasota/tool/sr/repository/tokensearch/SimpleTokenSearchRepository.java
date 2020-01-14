package pl.lasota.tool.sr.repository.tokensearch;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.tokensearch.specification.SpecificationSearchToken;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public class SimpleTokenSearchRepository<MODEL extends EntityBase> implements TokenSearchRepository<MODEL> {

    private final FullTextEntityManager fullTextEntityManager;
    private Class<MODEL> modelClass;

    public SimpleTokenSearchRepository(EntityManager em) {

        fullTextEntityManager = null;
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public Page<MODEL> find(SpecificationSearchToken specification, Pageable pageable) {

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