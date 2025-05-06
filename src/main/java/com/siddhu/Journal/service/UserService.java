package com.siddhu.Journal.service;

import com.siddhu.Journal.entity.ApiResponce;
import com.siddhu.Journal.entity.User;
import com.siddhu.Journal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private UserRepo repo;

    public void saveUser(User user){
        repo.save(user);
    }

    public List<User> getAll(){
        return repo.findAll();
    }

    public User getByUsername(String username){
        return repo.findByusername(username);
    }

    public void deleteUser(String username){
        repo.deleteById(getByUsername(username).getId());
    }

    public ResponseEntity<?> greeting(String name, String city){
        ApiResponce responce = weatherService.getWeather(city);
        String greetings = "";
        if(responce != null){
            greetings = "hi " + name + ", Weather feels like " + responce.getCurrent().getTemperature();
        }
        return ResponseEntity.ok(greetings);
    }
}
