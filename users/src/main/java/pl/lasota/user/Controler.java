package pl.lasota.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lasota.tool.orm.parser.ParserField;
import pl.lasota.tool.orm.security.AccessContext;
import pl.lasota.tool.orm.security.Context;

import java.util.LinkedList;
import java.util.List;

@RestController
public class Controler {

    private final CrudSecurityService crudService;

    private final SecuredSearchService searchService;

    private final UpdateService updateService;

    private final DeleteService deleteService;

    @Autowired
    private FullSearchService fullSearchService;

    public Controler(CrudSecurityService crudService, SecuredSearchService searchService, UpdateService updateService, DeleteService deleteService) {
        this.crudService = crudService;
        this.searchService = searchService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @GetMapping("/searchf")
    public Page<User> searchF(@RequestParam MultiValueMap<String, String> stringStringMultiValueMap) throws Exception {
        return fullSearchService.find(new ParserField().parse(stringStringMultiValueMap));
    }

    @GetMapping("/search/{context:.+}")
    public Page<UserDto> search(@RequestParam MultiValueMap<String, String> stringStringMultiValueMap, @PathVariable String context) throws Exception {
        Context context1 = new Context();
        context1.add(new AccessContext(context));
        return searchService.find(new ParserField().parse(stringStringMultiValueMap), context1);
    }

    @GetMapping("/update")
    public List<Long> update(@RequestParam MultiValueMap<String, String> stringStringMultiValueMap) throws Exception {


        return updateService.update(new ParserField().parse(stringStringMultiValueMap));
    }

    @GetMapping("/get/{id}/{context:.+}")
    public UserDto get(@PathVariable long id, @PathVariable String context) {
        Context context1 = new Context();
        context1.add(new AccessContext(context));
        return crudService.get(id, context1);
    }


    @GetMapping("/delete/{id}/{context:.+}")
    public Long de(@PathVariable long id, @PathVariable String context) {
        Context context1 = new Context();
        context1.add(new AccessContext(context));
        return crudService.delete(id, context1);
    }

    @GetMapping("/add/{name:.+}/{ulica:.+}")
    public UserDto add(@PathVariable String name, @PathVariable String ulica) {
        UserDto user = new UserDto();
        LinkedList<String> strings = new LinkedList<>();
        strings.add("adam");
        strings.add("patryk");
        strings.add("michal");
        user.setList(strings);
        user.setName(name);
        Address address = new Address();
        address.setStreet(ulica);
        user.setAddress(address);
        Context context = new Context();
        short s = 4;
        context.add(new AccessContext("adam.lasota", s));

        s = 2;
        context.add(new AccessContext("group", s));

        return crudService.save(user, context);
    }


    @GetMapping("/update/{id}/{name:.+}/{context:.+}")
    public UserDto update(@PathVariable long id, @PathVariable String name, @PathVariable String context) {
        UserDto user = new UserDto();
        user.setName(name);
        LinkedList<String> strings = new LinkedList<>();
        strings.add("adam");
        strings.add("mietek");
        strings.add("kurwa");
        strings.add("kurwa2");
        strings.add("kurwa3");
        user.setList(strings);

        user.setId(id);

        Context context1 = new Context();
        context1.add(new AccessContext(context));

        return crudService.update(user, context1);
    }
}
