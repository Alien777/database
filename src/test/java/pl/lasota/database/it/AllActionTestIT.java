package pl.lasota.database.it;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.database.mapping.DozerMapper;
import pl.lasota.database.mapping.DozerPageMapping;
import pl.lasota.database.repository.RepositoryAdapter;
import pl.lasota.database.repository.query.CriteriaBuilderImpl;
import pl.lasota.database.repository.query.Predicatable;
import pl.lasota.database.repository.query.Updatable;
import pl.lasota.database.repository.query.sort.Sortable;
import pl.lasota.database.service.base.AllAction;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.properties", classes = Application.class)
class AllActionTestIT {


    @Autowired
    private EntityManager em;


    @Test
    @Transactional
    void testAllAction() {
        AllAction<Shop, Shop, Shop, Shop> entityAll = new AllAction<>(new RepositoryAdapter<>(em),
                new DozerPageMapping<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                Shop.class) {
        };

        entityAll.save(new Shop("Adam", 1));
        entityAll.save(new Shop("Paulinka", 2));
        entityAll.save(new Shop("Maciej", 3));
        entityAll.save(new Shop("Wojtek", 4));
        entityAll.save(new Shop("Zośka", 5));
        entityAll.save(new Shop("Łukasz", 6));
        entityAll.save(new Shop("Kurpiel", 7));

        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
        Predicatable root = criteria.root(criteria.and(criteria.like("name", "Paulinka")));
        Page<Shop> shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Assertions.assertThat(shops.getContent())
                .hasSize(1)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Paulinka");


        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.all());
        shops = entityAll.find(criteria.build(root, criteria.sort("value", Sortable.Sort.DESC), criteria.page(0, 10)));
        Assertions.assertThat(shops.getContent()).isSortedAccordingTo((shop, t1) -> t1.getValue() - shop.getValue());


        root = criteria.root(criteria.and(criteria.gt("value", 3)));
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Assertions.assertThat(shops.getContent())
                .hasSize(4)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Wojtek", "Zośka", "Łukasz", "Kurpiel");


        root = criteria.root(criteria.and(criteria.gt("value", 3)));
        shops = entityAll.find(criteria.build(root, criteria.page(0, 2)));
        Assertions.assertThat(shops.getContent())
                .hasSize(2)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Wojtek", "Zośka");

        root = criteria.root(criteria.and(criteria.gt("value", 3)));
        shops = entityAll.find(criteria.build(root, criteria.page(1, 2)));
        Assertions.assertThat(shops.getContent())
                .hasSize(2)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Łukasz", "Kurpiel");


        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.and(criteria.like("name", "łukasz", criteria.lowerCase())));
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Assertions.assertThat(shops.getContent())
                .hasSize(1)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Łukasz");


        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.and(criteria.range("value", 2, 4)));
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Assertions.assertThat(shops.getContent())
                .hasSize(3)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Paulinka", "Maciej", "Wojtek");

        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.all());
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Assertions.assertThat(shops.getContent())
                .hasSize(7)
                .flatExtracting((Function<Shop, String>) Shop::getName);


        delete(entityAll);

        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.all());
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Assertions.assertThat(shops.getContent())
                .hasSize(4)
                .flatExtracting((Function<Shop, String>) Shop::getName).contains("Adam", "Zośka", "Łukasz", "Kurpiel");


        entityAll.update(1L, new Shop("zmiana", 33));

        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.all());
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Shop shop = shops.getContent().get(0);

        assertThat(shop.getName()).isEqualTo("zmiana");
        assertThat(shop.getValue()).isEqualTo(1);


    }

    @Transactional
    public void delete(AllAction<Shop, Shop, Shop, Shop> allAction) {
        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
        Predicatable value = criteria.root(criteria.and(criteria.range("value", 2, 4)));
        List<Long> delete = allAction.delete(criteria.build(value));
        assertThat(delete).hasSize(3);
    }

    @Transactional
    public void update(AllAction<Shop, Shop, Shop, Shop> allAction) {

        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
        Predicatable root = criteria.root(criteria.and(criteria.range("value", 0, 2)));
        Updatable set = criteria.set(  );
        List<Long> update = allAction.update(criteria.build(root, set));
        assertThat(update).hasSize(1);
    }

}