package com.siddhu.Journal.repository;

import com.siddhu.Journal.entity.entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface entryRepo extends JpaRepository<entry,Long> {
}
