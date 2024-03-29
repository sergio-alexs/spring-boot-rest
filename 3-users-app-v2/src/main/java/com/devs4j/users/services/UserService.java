package com.devs4j.users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devs4j.users.entities.UserEntity;
import com.devs4j.users.repositories.UserRepository;

/**
 * Service that manages business logic about Users, it uses the repository
 * {@link UserRepository}.
 * <p>
 * This Service maps the users of the database layer {@link UserEntity} to the
 * business logic layer and viceversa.
 *
 * @author jroldan
 * @version 1.0
 * @category Service
 * @since 22/12/28
 * @modify 23/01/03
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public List<UserEntity> getUsers() {
		return this.userRepo.findAll();
	}

	public Page<UserEntity> getUsersWithPagination(final int pageNum, final int pageSize) {
		return this.userRepo.findAll(PageRequest.of(pageNum, pageSize));
	}

	public List<UserEntity> getUsersWithOrder(final String attribute, final boolean isAsc) {
		final Sort.Direction typeOrder = (isAsc) ? Sort.Direction.ASC : Sort.Direction.DESC;
		return this.userRepo.findAll(Sort.by(typeOrder, attribute));
	}

	public Page<UserEntity> getUsersWithPaginationAndOrder(final int pageNum, final int pageSize,
			final String attribute, final boolean isAsc) {
		final Sort.Direction typeOrder = (isAsc) ? Sort.Direction.ASC : Sort.Direction.DESC;
		return this.userRepo.findAll(PageRequest.of(pageNum, pageSize, typeOrder, attribute));
	}

	public List<String> getUsernames() {
		return this.userRepo.findAllUserNames();
	}

	private UserEntity checkExistsUser(final Integer userId) {
		return this.userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				String.format("User with ID '%d' not found", userId)));
	}

	public UserEntity getUserById(final Integer userId) {
		return this.checkExistsUser(userId);
	}

	@Cacheable("users")
	public UserEntity getUserByUsername(final String username) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.userRepo.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("User with Username '%s' not found", username)));
	}

	public UserEntity getUserByUsernameAndPassword(final String username, final String password) {
		return this.userRepo.findByUsernameAndPassword(username, password)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("User with Username '%s' and Password '%s' not found", username, password)));
	}

	public UserEntity createUser(final UserEntity user) {
		final Optional<UserEntity> optUserToBeCreated = this.userRepo.findByUsername(user.getUsername());
		if (optUserToBeCreated.isEmpty()) {
			return this.userRepo.save(user);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				String.format("User with Username '%s' already exists", user.getUsername()));
	}

	public UserEntity updateUser(final Integer userId, final UserEntity user) {
		this.checkExistsUser(userId);
		return this.userRepo.save(user);
	}

	@CacheEvict("users")
	public void deleteUser(final Integer userId) {
		final UserEntity userToBeDeleted = this.checkExistsUser(userId);
		this.userRepo.delete(userToBeDeleted);
	}

}
