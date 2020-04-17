package pl.lasota.tool.sr.it;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.mapping.DozerPageMapping;
import pl.lasota.tool.sr.repository.RepositoryAdapter;
import pl.lasota.tool.sr.repository.query.CriteriaBuilderImpl;
import pl.lasota.tool.sr.repository.query.Predicatable;
import pl.lasota.tool.sr.repository.query.Updatable;
import pl.lasota.tool.sr.service.base.AllAction;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.properties", classes = Application.class)
class UpdateList {

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

    /// /NOT supported now
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void start(AllAction<Shop, Shop, Shop, Shop> entityAll) {

        CriteriaBuilderImpl criteria = CriteriaBuilderImpl.criteria();
        Predicatable predicatable = criteria.root(criteria.and(criteria.notEquals("products.name", "winogrono")));

        Updatable updatable = criteria.set(criteria.update("value", 233));
        List<Long> update = entityAll.update(criteria.build(predicatable, updatable));

    }

}