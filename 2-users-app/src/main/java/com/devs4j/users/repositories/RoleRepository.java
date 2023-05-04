package com.devs4j.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devs4j.users.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
