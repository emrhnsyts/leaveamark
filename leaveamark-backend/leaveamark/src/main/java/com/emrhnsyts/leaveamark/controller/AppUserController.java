package com.emrhnsyts.leaveamark.controller;
import com.emrhnsyts.leaveamark.request.AppUserCreateRequest;
import com.emrhnsyts.leaveamark.request.AppUserLoginRequest;
import com.emrhnsyts.leaveamark.response.AppUserResponse;
import com.emrhnsyts.leaveamark.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AppUserCreateRequest appUserCreateRequest) {
        appUserService.register(appUserCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody AppUserLoginRequest appUserLoginRequest) {
        return appUserService.login(appUserLoginRequest);
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId) {
        appUserService.deleteUser(userId);
        return ResponseEntity.ok("User deleted.");
    }

    @GetMapping("/{username}")
    public AppUserResponse getUser(@PathVariable("username") String username) {
        return appUserService.getUser(username);
    }
}
