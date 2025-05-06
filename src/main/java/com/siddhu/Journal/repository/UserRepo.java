package com.siddhu.Journal.repository;

import com.siddhu.Journal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByusername(String username);
}
