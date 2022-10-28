package com.emrhnsyts.leaveamark.repository;

import com.emrhnsyts.leaveamark.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);

    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
}
