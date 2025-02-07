package com.crud.crud.application.controller;

import com.crud.crud.application.exception.UserNotFoundException;
import com.crud.crud.application.model.User;
import com.crud.crud.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @PostMapping("/userLogin")
    boolean loginUser(@RequestBody User newUser) {
        System.out.println("username : " + newUser.getUsername() + "password : " + newUser.getPassword());
        return false;
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            return userRepository.save((user));
        }).orElseThrow(() -> new UserNotFoundException(id));
    }
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new  UserNotFoundException(id);

        }
        userRepository.deleteById(id);
        return "user with id " +id+ "has been deleted successfylly";

    }

}


