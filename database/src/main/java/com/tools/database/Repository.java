package com.tools.database;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.CacheMode;
import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.AllContext;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.hibernate.search.query.dsl.sort.SortFieldContext;
import org.hibernate.search.spatial.Coordinates;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


public abstract class Repository<OBJECT> {

    private static final AtomicBoolean INDEXED = new AtomicBoolean(false);
    private static final int MAX_OF_PAGE = 10;
    private EntityManager em;
    private final Class<OBJECT> type;

    public Repository(Class<OBJECT> type) {
        this.type = type;
    }

    protected void setRepository(EntityManager entityManager) {
        this.em = entityManager;

    }

    public void reindex(MassIndexerProgressMonitor massIndexerProgressMonitor) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

        fullTextEntityManager.createIndexer()
                .progressMonitor(massIndexerProgressMonitor)
                .purgeAllOnStart(true)
                .optimizeAfterPurge(true)
                .optimizeOnFinish(true)
                .batchSizeToLoadObjects(10)
                .typesToIndexInParallel(1)
                .idFetchSize(10)
                .threadsToLoadObjects(1)
                .cacheMode(CacheMode.NORMAL)
                .start();

    }

    public PageDTO<OBJECT> search(DefaultSearchProvider defaultSearchProvider, int maxResult) {


        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(type).get();

        BooleanJunction<BooleanJunction> bool = queryBuilder.bool();

        List<Field> boolFields = new LinkedList<>();
        defaultSearchProvider.set(boolFields);
        for (Field field : boolFields) {
            if (field instanceof BoolField) {
                if (((BoolField) field).getValue() == null) {
                    continue;
                }
                boolFieldProcess((BoolField<String>) field, queryBuilder, bool);
            }
            if (field instanceof RangeField) {
                if (((RangeField) field).from() == null && ((RangeField) field).to() == null) {
                    continue;
                }
                rangeFieldProcess((RangeField) field, queryBuilder, bool);
            }

            if (field instanceof RangeField) {
                if (((RangeField) field).from() == null && ((RangeField) field).to() == null) {
                    continue;
                }
                rangeFieldProcess((RangeField) field, queryBuilder, bool);
            }

        }

        AllContext all = queryBuilder.all();
        defaultSearchProvider.sort((field, distance, coordinates) -> {
            Query query = queryBuilder.spatial().onField(field).within(distance, Unit.KM).ofCoordinates(coordinates).createQuery();
            bool.must(query);
        });


        FullTextQuery fullTextQuery;
        if (!bool.isEmpty()) {
            fullTextQuery = fullTextEntityManager.createFullTextQuery(
                    bool.createQuery(), type);

        } else {
            fullTextQuery = fullTextEntityManager.createFullTextQuery(
                    all.createQuery(), type);
        }

        defaultSearchProvider.sort((field, distance, coordinates) ->
        {

        });

        defaultSearchProvider.sort(new SortingConfiguration() {
            @Override
            public void byDistance(String field, double distance, Coordinates coordinates) {
                Sort sort = queryBuilder.sort().byDistance().onField(field).fromCoordinates(coordinates).asc().createSort();
                fullTextQuery.setSort(sort);
            }

            @Override
            public void byField(String field, boolean type) {
                if (type) {
                    Sort sort = queryBuilder.sort().byField(field).asc().createSort();
                    fullTextQuery.setSort(sort);
                } else {
                    Sort sort = queryBuilder.sort().byField(field).desc().createSort();
                    fullTextQuery.setSort(sort);
                }
            }
        });

        List<OBJECT> resultList = fullTextQuery.setMaxResults(defaultSearchProvider.getSize() * maxResult).getResultList();

        int size1 = resultList.size();
        return new PageDTO<OBJECT>(resultList.stream().skip((defaultSearchProvider.getNumber() - 1) * defaultSearchProvider.getSize())
                .limit(defaultSearchProvider.getSize()).collect(Collectors.toList()), size1);
    }

    private void rangeFieldProcess(RangeField field, QueryBuilder qb, BooleanJunction<BooleanJunction> bool) {
        Query query = null;

        if (field.getTypeQuery() == BoolType.Range) {

            query = qb.range()
                    .onField(field.getField())
                    .from(field.from())
                    .to(field.from()).createQuery();
        }
        if (field.getTypeBool() == TypeBool.Must) {
            bool.must(query);
        }
        if (field.getTypeBool() == TypeBool.NotMust) {
            bool.must(query).not();
        }
    }

    private void boolFieldProcess(BoolField<String> field, QueryBuilder qb, BooleanJunction<BooleanJunction> bool) {
        Query query = null;

        if (field.getTypeQuery() == BoolType.Above) {
            query = qb.range()
                    .onField(field.getField())
                    .above(field.getValue())
                    .createQuery();
        }

        if (field.getTypeQuery() == BoolType.Below) {
            query = qb.range()
                    .onField(field.getField())
                    .below(field.getValue())
                    .createQuery();
        }

        if (field.getTypeQuery() == BoolType.SimpleQuery) {
            query = qb.simpleQueryString()
                    .onFields(field.getField(), field.getFields())
                    .matching(field.getValue())
                    .createQuery();
        }
        if (field.getTypeQuery() == BoolType.Phase) {
            query = qb.phrase()
                    .onField(field.getField())
                    .sentence(field.getValue())
                    .createQuery();
        }

        if (field.getTypeBool() == TypeBool.Must) {
            bool.must(query);
        }
        if (field.getTypeBool() == TypeBool.NotMust) {
            bool.must(query).not();
        }
        if (field.getTypeBool() == TypeBool.Should) {
            bool.should(query);
        }
    }


    public boolean isExisting(Long id) {
        OBJECT object = em.find(type, id);
        return object != null;
    }

    public OBJECT get(Long id) {
        return em.getReference(type, id);
    }


    public void create(OBJECT object) {
        em.persist(object);
    }

    public void update(OBJECT object) {
        em.merge(object);
    }

    public void delete(Long id) {
        OBJECT object = em.find(type, id);
        em.remove(object);
    }


}
