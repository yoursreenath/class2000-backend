package com.class2000.reunion.repository;

import com.class2000.reunion.model.GetTogether;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GetTogetherRepository extends JpaRepository<GetTogether, Long> {
    List<GetTogether> findAllByOrderByEventYearAsc();
}
