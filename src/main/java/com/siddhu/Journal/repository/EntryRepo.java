package com.siddhu.Journal.repository;

import com.siddhu.Journal.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepo extends JpaRepository<Entry,Long> {
}
