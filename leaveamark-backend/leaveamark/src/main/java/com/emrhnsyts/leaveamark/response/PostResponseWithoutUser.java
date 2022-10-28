package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseWithoutUser {
    private Long id;
    private String text;
    private List<LikeResponse> likes;
    private Long userId;
    private Date createdAt;

    public PostResponseWithoutUser(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.userId = post.getUser().getId();
        this.likes = post.getLikes().stream().map(like -> {
            return new LikeResponse(like);
        }).collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
    }
}
