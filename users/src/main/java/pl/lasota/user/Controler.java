package pl.lasota.user;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.lasota.tool.crud.serach.field.Field;
import pl.lasota.tool.crud.parser.ParserField;
import pl.lasota.user.service.CrudService;
import pl.lasota.user.service.SearchService;
import pl.lasota.user.service.UpdateService;

import java.util.*;

@RestController
public class Controler {

    private final CrudService crudService;

    private final SearchService searchService;

    private final UpdateService updateService;

    public Controler(CrudService crudService, SearchService searchService, UpdateService updateService) {
        this.crudService = crudService;
        this.searchService = searchService;
        this.updateService = updateService;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        return crudService.get(id);
    }

    @GetMapping("/search")
    public Page<User> search(@RequestParam MultiValueMap<String, String> allRequestParams) throws Exception {
        List<Field<?>> parse = new ParserField().parse(allRequestParams);
        return searchService.find(parse);

    }

    @GetMapping("/update")
    public List<User> update(@RequestParam MultiValueMap<String, String> allRequestParams) throws Exception {
        List<Field<?>> parse = new ParserField().parse(allRequestParams);
        return updateService.update(parse);

    }

    @GetMapping("/delete/{id}")
    public Long de(@PathVariable long id) {
        return crudService.delete(id);
    }


    @GetMapping("/add/{name}")
    public User get(@PathVariable String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(UUID.randomUUID().toString());
        return crudService.save(user);
    }
}
