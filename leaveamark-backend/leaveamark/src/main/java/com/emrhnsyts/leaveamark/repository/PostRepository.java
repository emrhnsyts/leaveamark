package com.emrhnsyts.leaveamark.repository;

import com.emrhnsyts.leaveamark.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserUsernameOrderByCreatedAtDesc(String username);

    List<Post> findByOrderByCreatedAtDesc();
}
