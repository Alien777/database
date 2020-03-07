package pl.lasota.tool.sr.it;

import com.google.common.collect.Ordering;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.mapping.DozerPageMapping;
import pl.lasota.tool.sr.repository.EntityRepository;
import pl.lasota.tool.sr.repository.query.*;
import pl.lasota.tool.sr.repository.query.field.SetField;
import pl.lasota.tool.sr.repository.query.sort.Sortable;
import pl.lasota.tool.sr.service.base.AllAction;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.properties", classes = Application.class)
class AllActionTestIT {


    @Autowired
    private EntityManager em;


    @Test
    @Transactional
    void crud() {
        AllAction<Shop, Shop, Shop, Shop> entityAll = new AllAction<>(new EntityRepository<>(em),
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
        assertThat(shops.getContent())
                .hasSize(1)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Paulinka");


        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.all());
        shops = entityAll.find(criteria.build(root, criteria.sort("value", Sortable.Sort.DESC), criteria.page(0, 10)));
        assertThat(shops.getContent()).isSortedAccordingTo((shop, t1) -> t1.getValue() - shop.getValue());


        root = criteria.root(criteria.and(criteria.gt("value", 3)));
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        assertThat(shops.getContent())
                .hasSize(4)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Wojtek", "Zośka", "Łukasz", "Kurpiel");


        root = criteria.root(criteria.and(criteria.gt("value", 3)));
        shops = entityAll.find(criteria.build(root, criteria.page(0, 2)));
        assertThat(shops.getContent())
                .hasSize(2)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Wojtek", "Zośka");

        root = criteria.root(criteria.and(criteria.gt("value", 3)));
        shops = entityAll.find(criteria.build(root, criteria.page(1, 2)));
        assertThat(shops.getContent())
                .hasSize(2)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Łukasz", "Kurpiel");


        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.and(criteria.like("name", "łukasz", criteria.lowerCase())));
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        assertThat(shops.getContent())
                .hasSize(1)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Łukasz");


        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.and(criteria.range("value", 2, 4)));
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        assertThat(shops.getContent())
                .hasSize(3)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Paulinka", "Maciej", "Wojtek");

        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.all());
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        assertThat(shops.getContent())
                .hasSize(7)
                .flatExtracting((Function<Shop, String>) Shop::getName);


        delete(entityAll);

        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.all());
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        assertThat(shops.getContent())
                .hasSize(4)
                .flatExtracting((Function<Shop, String>) Shop::getName).contains("Adam", "Zośka", "Łukasz", "Kurpiel");


        entityAll.update(1L, new Shop("zmiana", 33));

        criteria = CriteriaBuilderImpl.criteria();
        root = criteria.root(criteria.all());
        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Shop shop = shops.getContent().get(0);

        assertThat(shop.getName()).isEqualTo("zmiana");
        assertThat(shop.getValue()).isEqualTo(1);

//        update(entityAll);


//        criteria = CriteriaBuilderImpl.criteria();
//        root = criteria.root(criteria.all());
//        shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
//        assertThat(shops).flatExtracting((Function<Shop, String>) Shop::getName).contains("po zmianie");
    }

    @Transactional
    public void delete(AllAction<Shop, Shop, Shop, Shop> allAction) {
        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
        Predicatable value = criteria.root(criteria.and(criteria.range("value", 2, 4)));
        List<Long> delete = allAction.delete(criteria.build(value));
        assertThat(delete).hasSize(3);
    }

//    @Transactional
//    public void update(AllAction<Shop, Shop, Shop, Shop> allAction) {
//
//        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
//        Predicatable root = criteria.root(criteria.and(criteria.range("value", 0, 2)));
//        Updatable set = criteria.set(new SetField("name", "po zmianie"));
//        List<Long> update = allAction.update(criteria.build(root, set));
//        assertThat(update).hasSize(1);
//    }

}