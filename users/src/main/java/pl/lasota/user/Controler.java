package pl.lasota.user;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.parser.ParserField;

import java.util.List;
import java.util.UUID;

@RestController
public class Controler {

    private final CrudService crudService;

    private final SearchService searchService;

    private final UpdateService updateService;

    private final DeleteService deleteService;


    public Controler(CrudService crudService, SearchService searchService, UpdateService updateService, DeleteService deleteService) {
        this.crudService = crudService;
        this.searchService = searchService;
        this.updateService = updateService;
        this.deleteService = deleteService;
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

    @GetMapping("/delete")
    public List<Long> delete(@RequestParam MultiValueMap<String, String> allRequestParams) throws Exception {
        List<Field<?>> parse = new ParserField().parse(allRequestParams);
        return deleteService.delete(parse);

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
