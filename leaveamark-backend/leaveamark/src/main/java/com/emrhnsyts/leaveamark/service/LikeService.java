package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.AppUser;
import com.emrhnsyts.leaveamark.entity.Like;
import com.emrhnsyts.leaveamark.entity.Post;
import com.emrhnsyts.leaveamark.exception.GenericException;
import com.emrhnsyts.leaveamark.repository.LikeRepository;
import com.emrhnsyts.leaveamark.request.LikeCreateRequest;
import com.emrhnsyts.leaveamark.response.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final AppUserService appUserService;
    private final PostService postService;

    public void addLike(LikeCreateRequest likeCreateRequest) {
        AppUser user = appUserService.getUserForService(likeCreateRequest.getUserId());
        Post post = postService.getPostForService(likeCreateRequest.getPostId());
        likeRepository.save(Like.builder()
                .post(post)
                .user(user)
                .build());
    }

    public void deleteLike(Long likeId) {
        Optional<Like> optionalLike = likeRepository.findById(likeId);
        if (optionalLike.isPresent()) {
            likeRepository.delete(optionalLike.get());
        } else {
            throw new GenericException("Like not found.");
        }
    }

    public void deleteLikeByPostIdAndUserId(Long postId, Long userId) {
        AppUser user = appUserService.getUserForService(userId);
        Post post = postService.getPostForService(postId);

        Optional<Like> optionalLike = likeRepository.findByPostIdAndUserId(post.getId(), user.getId());
        if (!optionalLike.isPresent()) {
            throw new GenericException("Like not found.");
        } else {
            likeRepository.deleteById(optionalLike.get().getId());
        }
    }
}
