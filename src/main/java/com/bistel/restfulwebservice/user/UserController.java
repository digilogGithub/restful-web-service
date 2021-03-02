package com.bistel.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    // GET /users/1 or /users/10 -> String -> (auto convert) -> int
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%d] not found", id));
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
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
    }
}
