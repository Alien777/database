package pl.lasota.tool.sr.it;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.mapping.DozerPageMapping;
import pl.lasota.tool.sr.repository.EntityRepository;
import pl.lasota.tool.sr.repository.search.SimpleSearchRepository;
import pl.lasota.tool.sr.service.security.*;

import javax.persistence.EntityManager;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.properties", classes = Application.class)
class SearchActionTestIT {

//
//    @Autowired
//    private EntityManager em;
//    private SimpleProvidingPrivileges sp = new SimpleProvidingPrivileges();
//
//    @Test
//    @Transactional
//    void registrationWorksThroughAllLayers() throws Exception {
//        Shop shop = new Shop();
//        shop.setValue("ADAM");
//        AllSecurityAction<Shop, Shop, Shop, Shop> repo = new AllSecurityAction<>(
//                new EntityRepository<>(em),
//                new DozerPageMapping<>(Shop.class),
//                new DozerMapper<>(Shop.class),
//                new DozerMapper<>(Shop.class),
//                new DozerMapper<>(Shop.class),
//                Shop.class, sp);
//        repo.save(shop, new Auth("admin", sp.create(accessible -> accessible.update().read())));
//
//
//        Shop shop1 = new Shop();
//        shop1.setValue("ADAM2");
//
//        repo.update(1L, shop1, "admin");
//
//        Shop get = repo.get(1L, "admin");
//
//        System.out.println(get);
//
//    }

}