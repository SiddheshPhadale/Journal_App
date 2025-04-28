package com.siddhu.Journal.service;

import com.siddhu.Journal.entity.entry;
import com.siddhu.Journal.entity.user;
import com.siddhu.Journal.repository.entryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import com.siddhu.Journal.config.redisConfig;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class entryService {

    @Autowired
    private entryRepo repo;

    @Autowired
    private RedisService redisService;

    @Autowired
    private userService service;

    @CachePut(value = "entry", key = "#entry.id")
    public entry saveEntry(entry entry, String id){
        user user = service.getByUsername(id);
        entry.setDate(LocalDate.now());
        entry saved = repo.save(entry);
        user.getEntries().add(saved);
        service.saveUser(user);
        return saved;
    }

    @Cacheable(value = "entry", key = "#id")
    public Optional<entry> getById(Long id){
        return repo.findById(id);
    }

    @CacheEvict(value = "entry", key = "#id")
    public boolean delete(Long id, String username){
        try {
            user user = service.getByUsername(username);
            boolean b = user.getEntries().removeIf(x -> x.getId().equals(id));
            if(b) {
                repo.deleteById(id);
                service.saveUser(user);
                return true;
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @CachePut(value = "entry", key = "#id")
    public entry editById(Long id , entry myEntry, String username){
        user user = service.getByUsername(username);
        boolean b = user.getEntries().stream().anyMatch(x -> x.getId().equals(id));

        if(b){
            entry old = getById(id).orElse(null);

            if(old != null){
                old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
                old.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
                repo.save(old);
            }
            service.saveUser(user);
            return old;
        }else return null;
    }
}
