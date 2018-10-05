package com.koskou.demo.JPA.Controller;

import com.koskou.demo.Exceptions.UserNotFoundException;
import com.koskou.demo.JPA.Entity.PostJPA;
import com.koskou.demo.JPA.Entity.UserJPA;
import com.koskou.demo.JPA.Repository.PostRepository;
import com.koskou.demo.JPA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserControllerJPA {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    //retrieveAllUsers
    @GetMapping(value = "jpa/users")
    public List<UserJPA> retrieveAllUsers() {
        return userRepository.findAll();
    }

    //retrieveUser(int id)
    @GetMapping(value = "jpa/users/{id}")
    public Resource<UserJPA> retrieveUser(@PathVariable int id) {
        Optional<UserJPA> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new UserNotFoundException("id-"+id);
        }

        //"all-users", SERVER_PATH + "/users/
        //retrieveAllUsers
        Resource<UserJPA> resource = new Resource<UserJPA>(user.get());
        //Get the link
        ControllerLinkBuilder linkBuilder =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkBuilder.withRel("all-users"));
        //HATEOAS

        return resource;
    }

    @PostMapping(value = "jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserJPA user) {
        UserJPA savedUser = userRepository.save(user);

        //HTTP STATUS - CREATED
        //Mapping the URL
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")                  //Append something
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build ();
    }

    @DeleteMapping("jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }


    /*POST ENTITY*/
    //retrievePostByUser
    @GetMapping(value = "jpa/users/{id}/posts")
    public List<PostJPA> retrieveAllUsers(@PathVariable int id) {
        Optional<UserJPA> user = userRepository.findById(id);

        if (!user.isPresent()){
            throw new UserNotFoundException("id-"+id);
        }
        return user.get().getPosts();
    }

    @PostMapping(value = "jpa/users/{id}/posts")
    public ResponseEntity<Object> createUser(@PathVariable int id,
                                             @RequestBody PostJPA post) {
        Optional<UserJPA> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()){
            throw new UserNotFoundException("id-"+id);
        }

        UserJPA user = userOptional.get();
        //Best Practice HINT
        post.setUser(user);             //Set the post-user

        postRepository.save(post);      //Save the POST at DB
        //HTTP STATUS - CREATED
        //Mapping the URL
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")                  //Append something
                .buildAndExpand(post.getId())   //Change user to post
                .toUri();
        return ResponseEntity.created(location).build ();
    }
}
