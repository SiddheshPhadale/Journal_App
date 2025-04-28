package com.siddhu.Journal.service;

import com.siddhu.Journal.entity.apiResponce;
import com.siddhu.Journal.entity.user;
import com.siddhu.Journal.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {

    @Autowired
    private weatherService weatherService;

    @Autowired
    private userRepo repo;

    public void saveUser(user user){
        repo.save(user);
    }

    public List<user> getAll(){
        return repo.findAll();
    }

    public user getByUsername(String username){
        return repo.findByusername(username);
    }

    public void deleteUser(String username){
        repo.deleteById(getByUsername(username).getId());
    }

    public ResponseEntity<?> greeting(String name, String city){
        apiResponce responce = weatherService.getWeather(city);
        String greetings = "";
        if(responce != null){
            greetings = "hi " + name + ", Weather feels like " + responce.getCurrent().getTemperature();

        }
        return ResponseEntity.ok(greetings);
    }
}
