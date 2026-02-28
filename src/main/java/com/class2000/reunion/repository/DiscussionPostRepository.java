package com.class2000.reunion.repository;

import com.class2000.reunion.model.DiscussionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiscussionPostRepository extends JpaRepository<DiscussionPost, Long> {
    List<DiscussionPost> findAllByOrderByCreatedAtDesc();
}
