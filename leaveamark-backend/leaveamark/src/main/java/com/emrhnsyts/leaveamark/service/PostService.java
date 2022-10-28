package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.AppUser;
import com.emrhnsyts.leaveamark.entity.Post;
import com.emrhnsyts.leaveamark.exception.GenericException;
import com.emrhnsyts.leaveamark.repository.PostRepository;
import com.emrhnsyts.leaveamark.request.PostCreateRequest;
import com.emrhnsyts.leaveamark.response.PostResponse;
import com.emrhnsyts.leaveamark.response.PostResponseWithoutUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AppUserService appUserService;

    public void addPost(PostCreateRequest postCreateRequest) {
        AppUser user = appUserService.getUserForService(postCreateRequest.getUserId());
        if (!(user.getLastPostDate() == null)) {
            Calendar avaliableTime = Calendar.getInstance();
            avaliableTime.setTime(user.getLastPostDate());
            avaliableTime.add(Calendar.MILLISECOND, 86400000);
            Date currentTime = new Date();
            if (currentTime.after(avaliableTime.getTime())) {
                createPost(postCreateRequest, user);
            } else {
                throw new GenericException("User should wait until " + avaliableTime.getTime() + " to post.");
            }
        } else {
            createPost(postCreateRequest, user);
        }
    }

    public List<PostResponse> getPosts() {
        return postRepository.findByOrderByCreatedAtDesc().stream().map(post -> {
            return new PostResponse(post);
        }).collect(Collectors.toList());
    }


    public List<PostResponseWithoutUser> getPostsByUsername(String username) {
        AppUser user = appUserService.getUserByUsernameForService(username);
        return postRepository.findByUserUsernameOrderByCreatedAtDesc(user.getUsername())
                .stream().map((post -> {
                    return new PostResponseWithoutUser(post);
                })).collect(Collectors.toList());
    }

    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            throw new GenericException("Post not found.");
        } else {
            postRepository.delete(optionalPost.get());
        }
    }

    protected Post getPostForService(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            throw new GenericException("Post not found.");
        } else {
            return optionalPost.get();
        }
    }

    private void createPost(PostCreateRequest postCreateRequest, AppUser user) {
        Post post = new Post();
        post.setText(postCreateRequest.getText());
        post.setUser(user);
        postRepository.save(post);
        appUserService.changeLastPostDate(user);
    }

}
