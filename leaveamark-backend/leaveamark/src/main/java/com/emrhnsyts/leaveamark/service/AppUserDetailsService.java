package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.AppUser;
import com.emrhnsyts.leaveamark.repository.AppUserRepository;
import com.emrhnsyts.leaveamark.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if (optionalAppUser.isPresent()) {
            return new SecurityUser(optionalAppUser.get());
        }
        throw new UsernameNotFoundException("Username not found.");
    }
}
