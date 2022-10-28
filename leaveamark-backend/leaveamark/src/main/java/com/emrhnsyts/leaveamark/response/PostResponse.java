package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String text;
    private Long userId;
    private String username;
    private List<LikeResponse> likes;
    private Date createdAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.userId = post.getUser().getId();
        this.username = post.getUser().getUsername();
        this.likes = post.getLikes().stream().map(like -> {
            return new LikeResponse(like);
        }).collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
    }
}
