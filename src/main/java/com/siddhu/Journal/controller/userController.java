package com.siddhu.Journal.controller;

import com.siddhu.Journal.entity.user;
import com.siddhu.Journal.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService service;

    @GetMapping
    public List<user> getAllUser(){
        return service.getAll();
    }

    @GetMapping("/{username}/{city}")
    public ResponseEntity<?> greetings(@PathVariable String username, @PathVariable String city){
        user user = service.getByUsername(username);
        if(user != null){
            return service.greeting(username, city);
        }else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public user createUser(@RequestBody user user){
        service.saveUser(user);
        return user;
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody user user, @PathVariable String username){
        user old = service.getByUsername(username);

        if(old != null){
            old.setUsername(user.getUsername());
        }

        service.saveUser(old);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username){
        service.deleteUser(username);
    }
}
