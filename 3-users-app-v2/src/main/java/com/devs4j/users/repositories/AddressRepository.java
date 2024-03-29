package com.devs4j.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.devs4j.users.entities.AddressEntity;

/**
 * Interface for CRUD operations on a repository for a {@link AddressEntity}.
 *
 * @author jroldan
 * @version 1.0
 * @category Repository
 * @since 22/12/27
 * @upgrade 23/01/24
 */
public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {

	@Query("SELECT a FROM AddressEntity a WHERE a.profile.user.id=?1 AND a.profile.id=?2")
	List<AddressEntity> findByProfileAndUserId(Integer userId, Integer profileId);

}
