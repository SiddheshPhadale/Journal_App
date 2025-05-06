package com.siddhu.Journal.service;

import com.siddhu.Journal.entity.Entry;
import com.siddhu.Journal.entity.User;
import com.siddhu.Journal.repository.EntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
//import com.siddhu.Journal.config.redisConfig;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class EntryService {

    @Autowired
    private EntryRepo repo;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService service;

    @CachePut(value = "entry", key = "#entry.id")
    public Entry saveEntry(Entry entry, String id){
        User user = service.getByUsername(id);
        Entry saved = repo.save(entry);
        user.getEntries().add(saved);
        service.saveUser(user);
        return saved;
    }

    @Cacheable(value = "entry", key = "#id")
    public Optional<Entry> getById(Long id){
        return repo.findById(id);
    }

    @CacheEvict(value = "entry", key = "#id")
    public boolean delete(Long id, String username){
        try {
            User user = service.getByUsername(username);
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
    public Entry editById(Long id , Entry myEntry, String username){
        User user = service.getByUsername(username);
        boolean b = user.getEntries().stream().anyMatch(x -> x.getId().equals(id));

        if(b){
            Entry old = getById(id).orElse(null);

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
