package com.siddhu.Journal.controller;

import com.siddhu.Journal.entity.entry;
import com.siddhu.Journal.entity.user;
import com.siddhu.Journal.service.entryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.siddhu.Journal.service.userService;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class entryController {

    @Autowired
    private entryService service;
    @Autowired
    private userService userService;

    @GetMapping("/{username}")
    public List<entry> getAllByUser(@PathVariable String username){
        user user = userService.getByUsername(username);

        return user.getEntries();
    }

    @PostMapping("/{username}")
    public ResponseEntity<entry> addEntry(@RequestBody entry myEntry, @PathVariable String username){
        entry entry = service.saveEntry(myEntry, username);
        if(entry != null){
            return ResponseEntity.ok(entry);
        }else return ResponseEntity.notFound().build();
    }

    @PutMapping("/{username}/{id}")
    public ResponseEntity<entry> editById(@PathVariable Long id, @RequestBody entry myEntry, @PathVariable String username){
        entry updatedEntry = service.editById(id, myEntry, username);
        if(updatedEntry != null){
            return ResponseEntity.ok(updatedEntry);
        }else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @PathVariable String username){
        boolean delete = service.delete(id, username);
        if(delete){
            return ResponseEntity.ok().build();
        }else return ResponseEntity.notFound().build();
    }
}
