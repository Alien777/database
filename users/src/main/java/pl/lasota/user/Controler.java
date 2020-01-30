package pl.lasota.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lasota.tool.sr.dynamicrepository.partsql.column.Column;
import pl.lasota.tool.sr.dynamicrepository.ExecutorQuery;
import pl.lasota.tool.sr.dynamicrepository.partsql.constrains.NotNull;
import pl.lasota.tool.sr.dynamicrepository.partsql.constrains.PrimaryKey;
import pl.lasota.tool.sr.dynamicrepository.partsql.constrains.Unique;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Integer;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Serial;
import pl.lasota.tool.sr.dynamicrepository.signaturesql.AddColumnSignature;
import pl.lasota.tool.sr.dynamicrepository.signaturesql.CreateTableSignature;
import pl.lasota.tool.sr.parser.ParserField;
import pl.lasota.tool.sr.security.AccessContext;
import pl.lasota.tool.sr.security.Context;

import javax.persistence.EntityManager;

@RestController
public class Controler {
    private final SearchSelfServiceCar searchSecurityDelegatorCar;

    private final SearchSelfServiceUser searchSecurityDelegatorUser;

    private final CrudSecurityServiceUser crudSecurityServiceUser;

    private final SearchServiceCar searchServiceCar;

    private final SearchServiceUser searchServiceUser;

    private final CrudSecurityServiceCar crudSecurityServiceCar;


    public Controler(CrudSecurityServiceUser crudSecurityServiceUser, SearchServiceCar searchServiceCar, SearchServiceUser searchServiceUser, CrudSecurityServiceCar crudSecurityServiceCar, SearchSelfServiceCar searchSecurityDelegatorCar, SearchSelfServiceUser searchSecurityDelegatorUser) {
        this.crudSecurityServiceUser = crudSecurityServiceUser;
        this.searchServiceCar = searchServiceCar;
        this.searchServiceUser = searchServiceUser;
        this.crudSecurityServiceCar = crudSecurityServiceCar;
        this.searchSecurityDelegatorCar = searchSecurityDelegatorCar;
        this.searchSecurityDelegatorUser = searchSecurityDelegatorUser;
    }


    @GetMapping("/user/search")
    public Page<UserDto> searchUser(@RequestParam MultiValueMap<String, String> stringStringMultiValueMap) throws Exception {
        return searchServiceUser.find(new ParserField().parse(stringStringMultiValueMap));
    }

    @GetMapping("/car/search/{context:.+}")
    public Page<Car> searchCar(@PathVariable String context, @RequestParam MultiValueMap<String, String> stringStringMultiValueMap) throws Exception {
        Context contextC = new Context();
        contextC.add(new AccessContext(context));
        return searchSecurityDelegatorCar.find(new ParserField().parse(stringStringMultiValueMap), contextC);
    }

    @GetMapping("/user/search/{context:.+}")
    public Page<UserDto> searchUserSelf(@PathVariable String context, @RequestParam MultiValueMap<String, String> stringStringMultiValueMap) throws Exception {
        Context contextC = new Context();
        contextC.add(new AccessContext(context));
        return searchSecurityDelegatorUser.find(new ParserField().parse(stringStringMultiValueMap), contextC);
    }

    @GetMapping("/car/search")
    public Page<Car> searchCarSelf(@RequestParam MultiValueMap<String, String> stringStringMultiValueMap) throws Exception {
        return searchServiceCar.find(new ParserField().parse(stringStringMultiValueMap));
    }


    @GetMapping("/car/add")
    public Car car() {

        Context context = new Context();
        short s = 4;
        context.add(new AccessContext("adam.lasotacar", s));

        s = 2;
        context.add(new AccessContext("groupcar", s));
        Car car = new Car();
        car.setColor("roz");
        return crudSecurityServiceCar.save(car, context);
    }


    @GetMapping("/user/add/{name:.+}/{ulica:.+}")
    public UserDto add(@PathVariable String name, @PathVariable String ulica) {
        UserDto user = new UserDto();


        user.setName(name);
        Address address = new Address();
        address.setStreet(ulica);
        user.setAddress(address);
        Context context = new Context();
        short s = 4;
        context.add(new AccessContext("adam.lasota", s));

        s = 2;
        context.add(new AccessContext("group", s));

        return crudSecurityServiceUser.save(user, context);
    }

    @Autowired
    EntityManager entityManager;

    @GetMapping("/execute")
    @Transactional
    public void execute() {
        Column id = new Column("nowa_kolumna", new Integer(), new NotNull());

        AddColumnSignature addColumnSignature = new AddColumnSignature("nowa_tabela", id);
        new ExecutorQuery(entityManager).builder(addColumnSignature);
    }
}
