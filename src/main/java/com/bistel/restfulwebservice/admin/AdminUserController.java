package com.bistel.restfulwebservice.admin;

import com.bistel.restfulwebservice.user.User;
import com.bistel.restfulwebservice.user.UserDaoService;
import com.bistel.restfulwebservice.user.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService userDaoService;

    public AdminUserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    // GET /users/1 or /users/10 -> String -> (auto convert) -> int
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%d] not found", id));
        }
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

  /*  @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.saveUser(user);

        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(locationUri).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userDaoService.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%d] not found", id));
        }
    }*/
}
