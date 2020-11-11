package pl.lasota.database.it;

import org.assertj.core.api.Assertions;
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
import pl.lasota.database.service.base.AllAction;

import javax.persistence.EntityManager;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.properties", classes = Application.class)
class SearchInListIT {

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    void crud() {
        AllAction<Shop, Shop, Shop, Shop> entityAll = new AllAction<>(new RepositoryAdapter<>(em),
                new DozerPageMapping<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                new DozerMapper<>(Shop.class),
                Shop.class) {
        };

        init(entityAll);
        start(entityAll);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void init(AllAction<Shop, Shop, Shop, Shop> entityAll) {
        Shop adam = new Shop();
        adam.setName("Wartosc");
        adam.getProducts().add(new Product("winogrono"));
        adam.getProducts().add(new Product("samochod"));
        entityAll.save(adam);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void start(AllAction<Shop, Shop, Shop, Shop> entityAll) {

        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
        Predicatable root = criteria.root(criteria.and(criteria.notEquals("products.name", "winogrono")));
        Page<Shop> shops = entityAll.find(criteria.build(root, criteria.page(0, 10)));
        Assertions.assertThat(shops.getContent())
                .hasSize(1)
                .flatExtracting((Function<Shop, String>) Shop::getName)
                .contains("Wartosc");
    }

}