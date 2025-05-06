package com.siddhu.Journal.controller;

import com.siddhu.Journal.entity.User;
import com.siddhu.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getAllUser(){
        return service.getAll();
    }

    @GetMapping("/{username}/{city}")
    public ResponseEntity<?> greetings(@PathVariable String username, @PathVariable String city){
        User user = service.getByUsername(username);
        if(user != null){
            return service.greeting(username,city);
        }else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        service.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
        User old = service.getByUsername(username);

        if(old != null){
            old.setUsername(user.getUsername());
            service.saveUser(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username){
        service.deleteUser(username);
    }
}
