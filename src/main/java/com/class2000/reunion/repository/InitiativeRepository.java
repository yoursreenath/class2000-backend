package com.class2000.reunion.repository;

import com.class2000.reunion.model.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InitiativeRepository extends JpaRepository<Initiative, Long> {
    List<Initiative> findAllByOrderByCreatedAtDesc();
    List<Initiative> findByTypeIgnoreCase(String type);
}
