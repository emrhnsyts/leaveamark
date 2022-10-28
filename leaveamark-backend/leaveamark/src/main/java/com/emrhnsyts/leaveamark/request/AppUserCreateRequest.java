package com.emrhnsyts.leaveamark.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserCreateRequest {
    @NotBlank(message = "Username can not be blank.")
    @Length(min = 5, max = 30, message = "Username must be between 5 and 30 letters.")
    private String username;
    @NotBlank(message = "Password can not be blank.")
    @Length(min = 5, max = 20, message = "Password must be between 5 and 20 letters.")
    private String password;
    @Email(message = "Email should be in a valid form.")
    @NotBlank(message = "Email can not be blank.")
    private String email;
}
