package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.AppUser;
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
public class AppUserResponse {
    private Long id;
    private String username;
    private String email;
    private Date createdAt;
    private List<String> roles;

    public AppUserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.createdAt = appUser.getCreatedAt();
        this.roles = appUser.getRoles().stream().map(role -> {
            return role.getName();
        }).collect(Collectors.toList());
    }
}
