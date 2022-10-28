package com.emrhnsyts.leaveamark.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeCreateRequest {
    private Long userId;
    private Long postId;
}

