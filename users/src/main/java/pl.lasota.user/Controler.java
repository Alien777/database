package pl.lasota.user;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lasota.tool.crud.serach.ConditionString;
import pl.lasota.tool.crud.serach.FieldString;
import pl.lasota.tool.crud.serach.PredictionType;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
public class Controler {

    private final UserCrud userCrudService;

    private UserSearch userSearchService;

    public Controler(UserCrud userCrudService, UserSearch userSearchService) {
        this.userCrudService = userCrudService;
        this.userSearchService = userSearchService;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        return userCrudService.get(id);
    }

    @GetMapping("/search/{name}/{name1}")
    public List<User> search(@PathVariable String name, @PathVariable String name1) {
        List<FieldString> fieldStrings = new LinkedList<>();
        fieldStrings.add(new FieldString("name",name, PredictionType.AND, ConditionString.EQUALS));
        fieldStrings.add(new FieldString("name",name1, PredictionType.OR, ConditionString.EQUALS));
        return  null;

    }

    @PostMapping("/update")
    public User get(User user) {
        return userCrudService.update(user);
    }

    @GetMapping("/add/{name}")
    public User get(@PathVariable String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(UUID.randomUUID().toString());
        return userCrudService.save(user);
    }
}
