package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.Role;
import com.emrhnsyts.leaveamark.exception.GenericException;
import com.emrhnsyts.leaveamark.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRole(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (!optionalRole.isPresent()) {
            throw new GenericException("Role not found.");
        } else {
            return optionalRole.get();
        }
    }
}
