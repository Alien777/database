package pl.lasota.database.it;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.database.mapping.DozerMapper;
import pl.lasota.database.mapping.DozerPageMapping;
import pl.lasota.database.repository.RepositoryAdapter;
import pl.lasota.database.repository.query.CriteriaBuilderImpl;
import pl.lasota.database.repository.query.Predicatable;
import pl.lasota.database.repository.query.QueryCriteria;
import pl.lasota.database.repository.query.Updatable;
import pl.lasota.database.service.base.AllAction;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.properties", classes = Application.class)
@Ignore
class UpdateListIT {

    @Autowired
    private EntityManager em;

//    @Test
//    @Ignore("Test not support update nested entity java.lang.IllegalArgumentException: UPDATE/DELETE criteria queries cannot define joins")
//    @Transactional
//    void nested_update() {
//        AllAction<Shop, Shop, Shop, Shop> entityAll = new AllAction<>(new RepositoryAdapter<>(em),
//                new DozerPageMapping<>(Shop.class),
//                new DozerMapper<>(Shop.class),
//                new DozerMapper<>(Shop.class),
//                new DozerMapper<>(Shop.class),
//                Shop.class) {
//        };
//
//        initNested(entityAll);
//        updateNested(entityAll);
//    }
//
//
//
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void initNested(AllAction<Shop, Shop, Shop, Shop> entityAll) {
//        Shop adam = new Shop();
//        adam.setName("Wartosc");
//        adam.getProducts().add(new Product("winogrono"));
//        adam.getProducts().add(new Product("samochod"));
//        entityAll.save(adam);
//    }
//
//    //NOT supported now
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void updateNested(AllAction<Shop, Shop, Shop, Shop> entityAll) {
//
//        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
//        Predicatable predicatable = criteria.root(criteria.and(criteria.notEquals("products.name", "winogrono")));
//
//        Updatable updatable = criteria.set(criteria.update("value", 233));
//        entityAll.update(criteria.build(predicatable, updatable));
//
//    }
//
//
//

    @Test
    @Transactional
    void updateIfExistAnnotationIsUpdate() {
        AllAction<Shop, Shop, Shop, Shop> entityAll = new AllAction<>(new RepositoryAdapter<>(em),
                new DozerPageMapping<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                Shop.class) {
        };

        initIfExistAnnotationIsUpdate(entityAll);
        updateIfExistAnnotationIsUpdate(entityAll);
        checkIfExistAnnotationIsUpdate(entityAll);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initIfExistAnnotationIsUpdate(AllAction<Shop, Shop, Shop, Shop> entityAll) {
        Shop aldi = new Shop();
        aldi.setName("aldi");
        entityAll.save(aldi);

        Shop biedronka = new Shop();
        biedronka.setName("Biedronka");
        entityAll.save(biedronka);

        Shop lidl = new Shop();
        lidl.setName("Lidl");
        entityAll.save(lidl);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateIfExistAnnotationIsUpdate(AllAction<Shop, Shop, Shop, Shop> entityAll) {

        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
        Predicatable predicatable = criteria.root(criteria.and(criteria.like("name", "Biedr")));

        Updatable updatable = criteria.set(criteria.update("value", 233));
        entityAll.update(criteria.build(predicatable, updatable));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkIfExistAnnotationIsUpdate(AllAction<Shop, Shop, Shop, Shop> entityAll) {

        CriteriaBuilderImpl criteriaCheck = CriteriaBuilderImpl.criteria();
        Predicatable predicatableCheck = criteriaCheck.root(criteriaCheck.and(criteriaCheck.like("name", "Biedr")));

        QueryCriteria queryCriteriacheck = criteriaCheck.build(predicatableCheck, CriteriaBuilderImpl.criteria().page(0));

        Page<Shop> shops = entityAll.find(queryCriteriacheck);
        shops.get().forEach(shop -> assertThat(shop.getValue()).isNull());

    }


    @Test
    @Transactional
    public void updateIfNotExistAnnotationIsUpdateTest() {
        initIfNotExistAnnotationIsUpdate(em);
        updateIfNotExistAnnotationIsUpdate(em);
        checkIfNotExistAnnotationIsUpdate(em);
    }
    public void initIfNotExistAnnotationIsUpdate(EntityManager em) {

        AllAction<Shop, Shop, Shop, Shop> entityAll = new AllAction<>(new RepositoryAdapter<>(em),
                new DozerPageMapping<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                Shop.class) {
        };


        Shop aldi = new Shop();
        aldi.setName("aldi");
        entityAll.save(aldi);

        Shop biedronka = new Shop();
        biedronka.setName("Biedronka");
        entityAll.save(biedronka);

        Shop lidl = new Shop();
        lidl.setName("Lidl");
        entityAll.save(lidl);
    }

    public void updateIfNotExistAnnotationIsUpdate(EntityManager em) {
        AllAction<Shop, Shop, Shop, Shop> entityAll = new AllAction<>(new RepositoryAdapter<>(em),
                new DozerPageMapping<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                Shop.class) {
        };

        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
        Predicatable predicatable = criteria.root(criteria.and(criteria.like("name", "edronk")));

        Updatable updatable = criteria.set(criteria.update("secondName", "New Name"));
        entityAll.update(criteria.build(predicatable, updatable));

    }


    public void checkIfNotExistAnnotationIsUpdate(EntityManager em) {
        AllAction<Shop, Shop, Shop, Shop> entityAll = new AllAction<>(new RepositoryAdapter<>(em),
                new DozerPageMapping<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                Shop.class) {
        };

        CriteriaBuilderImpl criteriaCheck = CriteriaBuilderImpl.criteria();
        Predicatable predicatableCheck = criteriaCheck.root(criteriaCheck.and(criteriaCheck.like("secondName", "New Name")));

        QueryCriteria queryCriteriaCheck = criteriaCheck.build(predicatableCheck, CriteriaBuilderImpl.criteria().page(0));

        Page<Shop> shops = entityAll.find(queryCriteriaCheck);

        assertThat(shops.getTotalElements()).isEqualTo(1);


    }

}