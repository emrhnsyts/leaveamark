package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.AppUser;
import com.emrhnsyts.leaveamark.exception.GenericException;
import com.emrhnsyts.leaveamark.repository.AppUserRepository;
import com.emrhnsyts.leaveamark.request.AppUserCreateRequest;
import com.emrhnsyts.leaveamark.request.AppUserLoginRequest;
import com.emrhnsyts.leaveamark.response.AppUserResponse;
import com.emrhnsyts.leaveamark.security.JwtTokenUtil;
import com.emrhnsyts.leaveamark.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;


    public AppUserResponse getUser(String username) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if (!optionalAppUser.isPresent()) {
            throw new GenericException("Username not found.");
        } else {
            return new AppUserResponse(optionalAppUser.get());
        }
    }

    public String login(AppUserLoginRequest appUserLoginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    appUserLoginRequest.getUsername(), appUserLoginRequest.getPassword()
            ));
            AppUser appUser = findUserByUsername(appUserLoginRequest.getUsername());
            if (!appUser.getEnabled()) {
                throw new GenericException("Please verify your account.");
            } else {
                final SecurityUser securityUser = new SecurityUser(appUser);
                final String jwt = jwtTokenUtil.generateToken(securityUser.getAppUser());
                return jwt;
            }
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        }
    }


    public void register(AppUserCreateRequest appUserCreateRequest) {
        AppUser appUser = new AppUser();
        appUser.setPassword(passwordEncoder.encode(appUserCreateRequest.getPassword()));
        appUser.setUsername(appUserCreateRequest.getUsername());
        appUser.setEmail(appUserCreateRequest.getEmail());
        appUser.setRoles(List.of(roleService.getRole("ROLE_USER")));
        appUser.setEnabled(false);
        appUserRepository.save(appUser);
        emailService.sendEmailTo(appUser);
    }


    public void deleteUser(Long userId) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(userId);
        if (!optionalAppUser.isPresent()) {
            throw new GenericException("User not found.");
        } else {
            appUserRepository.deleteById(optionalAppUser.get().getId());
        }
    }

    protected AppUser getUserForService(Long userId) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(userId);
        if (!optionalAppUser.isPresent()) {
            throw new GenericException("User not found.");
        } else {
            return optionalAppUser.get();
        }
    }

    protected AppUser getUserByUsernameForService(String username) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if (!optionalAppUser.isPresent()) {
            throw new GenericException("Username not found.");
        } else {
            return optionalAppUser.get();
        }
    }

    protected void changeLastPostDate(AppUser appUser) {
        appUser.setLastPostDate(new Date());
        appUserRepository.save(appUser);
    }


    public AppUser findUserByUsername(String username) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if (!optionalAppUser.isPresent()) {
            throw new GenericException("Username not found.");
        }
        return optionalAppUser.get();
    }


}
