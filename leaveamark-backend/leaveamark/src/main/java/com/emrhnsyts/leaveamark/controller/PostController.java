package com.emrhnsyts.leaveamark.controller;

import com.emrhnsyts.leaveamark.request.PostCreateRequest;
import com.emrhnsyts.leaveamark.response.PostResponse;
import com.emrhnsyts.leaveamark.response.PostResponseWithoutUser;
import com.emrhnsyts.leaveamark.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity addPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
        postService.addPost(postCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{username}")
    public List<PostResponseWithoutUser> getPostsByUsername(@PathVariable String username) {
        return postService.getPostsByUsername(username);
    }

    @DeleteMapping("/{postId}/{userId}")
    @PreAuthorize("#userId == authentication.principal.appUser.getId() or hasRole('ADMIN')")
    public ResponseEntity deletePostById(@PathVariable("postId") Long postId,
                                         @PathVariable("userId") Long userId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
