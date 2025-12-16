package com.hardik.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hardik.auth.entitiy.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
