package com.emrhnsyts.leaveamark.repository;

import com.emrhnsyts.leaveamark.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
