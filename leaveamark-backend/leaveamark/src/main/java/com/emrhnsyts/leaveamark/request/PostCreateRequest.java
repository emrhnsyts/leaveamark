package com.emrhnsyts.leaveamark.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateRequest {
    @NotBlank(message = "Post text can not be blank.")
    @Length(min = 3, max = 50, message = "Post length must be between 3 and 50 letters.")
    private String text;
    private Long userId;
}
