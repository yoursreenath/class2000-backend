package com.class2000.reunion.repository;

import com.class2000.reunion.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String first, String last);
    List<Teacher> findBySubjectIgnoreCase(String subject);
}
