package com.siddhu.Journal.repository;

import com.siddhu.Journal.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<user,Long> {
    user findByusername(String username);
}
