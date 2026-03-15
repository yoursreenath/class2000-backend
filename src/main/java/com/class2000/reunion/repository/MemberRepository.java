package com.class2000.reunion.repository;

import com.class2000.reunion.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByOrderByFirstNameAsc();
    List<Member> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrderByFirstNameAsc(String first, String last);
}
