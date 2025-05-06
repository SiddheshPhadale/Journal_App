package com.siddhu.Journal.controller;

import com.siddhu.Journal.entity.Entry;
import com.siddhu.Journal.entity.User;
import com.siddhu.Journal.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.siddhu.Journal.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class EntryController {

    @Autowired
    private EntryService service;
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<List<Entry>> getAllByUser(@PathVariable String username){
        User user = userService.getByUsername(username);
        if (user != null) return new ResponseEntity<>(user.getEntries(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<Entry> addEntry(@RequestBody Entry myEntry, @PathVariable String username){
        Entry entry = service.saveEntry(myEntry, username);
        if(entry != null){
            return ResponseEntity.ok(entry);
        }else return ResponseEntity.notFound().build();
    }

    @PutMapping("/{username}/{id}")
    public ResponseEntity<Entry> editById(@PathVariable Long id, @RequestBody Entry myEntry, @PathVariable String username){
        Entry updatedEntry = service.editById(id, myEntry, username);
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
