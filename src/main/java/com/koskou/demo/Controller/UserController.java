package com.koskou.demo.Controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.koskou.demo.Entity.User;
import com.koskou.demo.Exceptions.UserNotFoundException;
import com.koskou.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //retrieveAllUsers
    @GetMapping(value = "/users")
    public List<User> retrieveAllUsers() {
        return userService.findAll();
    }

    //retrieveUser(int id)
    @GetMapping(value = "/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = userService.findOne(id);
        if (user == null){
            throw new UserNotFoundException("id-"+id);
        }

        //"all-users", SERVER_PATH + "/users/
        //retrieveAllUsers
        Resource<User> resource = new Resource<User>(user);
        //Get the link
        ControllerLinkBuilder linkBuilder =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkBuilder.withRel("all-users"));
        //HATEOAS

        return resource;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        //HTTP STATUS - CREATED
        //Mapping the URL
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")                  //Append something
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build ();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userService.deleteById(id);
        if (user == null){
            throw new UserNotFoundException("id-"+id);
        }
    }
}
