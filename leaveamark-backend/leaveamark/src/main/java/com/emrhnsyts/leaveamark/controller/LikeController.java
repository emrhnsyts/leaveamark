package com.emrhnsyts.leaveamark.controller;

import com.emrhnsyts.leaveamark.request.LikeCreateRequest;
import com.emrhnsyts.leaveamark.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity addLike(@RequestBody LikeCreateRequest likeCreateRequest) {
        likeService.addLike(likeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity deleteLike(@PathVariable("likeId") Long likeId) {
        likeService.deleteLike(likeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity deleteLikeByPostIdAndUserId(@PathVariable("postId") Long postId,
                                                      @PathVariable("userId") Long userId) {
        likeService.deleteLikeByPostIdAndUserId(postId, userId);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{postId}")
//    public List<LikeResponseWithoutPostId> getLikesByPostId(@PathVariable("postId") Long postId) {
//        return likeService.getLikesByPostId(postId);
//    }
}
