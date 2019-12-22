package pl.lasota.user;


import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.lasota.tool.crud.serach.parser.ParserField;

import java.util.*;

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

    @GetMapping("/search")
    public Page<UserDto> search(@RequestParam MultiValueMap<String, String> allRequestParams) throws Exception {
        return userSearchService.find(new ParserField().parse(allRequestParams));

    }

    @PostMapping("/update")
    public User get(User user) {
        return userCrudService.update(user);
    }

    @GetMapping("/delete/{id}")
    public void de(@PathVariable long id) {
        userCrudService.delete(id);
    }


    @GetMapping("/add/{name}")
    public User get(@PathVariable String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(UUID.randomUUID().toString());
        return userCrudService.save(user);
    }
}
