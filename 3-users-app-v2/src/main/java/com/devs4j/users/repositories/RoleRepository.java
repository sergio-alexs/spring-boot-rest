package com.devs4j.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devs4j.users.entities.RoleEntity;

/**
 * Interface for CRUD operations on a repository for a {@link RoleEntity}.
 *
 * @author jroldan
 * @version 1.0
 * @category Repository
 * @since 22/12/27
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

}
