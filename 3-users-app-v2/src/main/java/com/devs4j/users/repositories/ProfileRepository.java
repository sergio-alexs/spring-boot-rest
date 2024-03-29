package com.devs4j.users.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.devs4j.users.entities.ProfileEntity;

/**
 * Interface for CRUD operations on a repository for a {@link ProfileEntity}.
 *
 * @author jroldan
 * @version 1.0
 * @category Repository
 * @since 22/12/27
 * @upgrade 23/01/24
 */
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

	@Query("SELECT p FROM ProfileEntity p WHERE p.user.id=?1 AND p.id=?2")
	Optional<ProfileEntity> findByUserIdAndProfileId(Integer userId, Integer profileId);

}
