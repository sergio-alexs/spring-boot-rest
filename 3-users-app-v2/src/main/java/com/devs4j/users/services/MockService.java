package com.devs4j.users.services;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devs4j.users.entities.RoleEntity;
import com.devs4j.users.entities.UserEntity;
import com.devs4j.users.entities.UserInRoleEntity;
import com.devs4j.users.repositories.RoleRepository;
import com.devs4j.users.repositories.UserInRoleRepository;
import com.devs4j.users.repositories.UserRepository;
import com.github.javafaker.Faker;

/**
 * Service that manages mock about Data.
 *
 * @author jroldan
 * @version 1.0
 * @category Service
 * @since 22/12/28
 * @upgrade 23/01/30
 */
@Service
public class MockService {

	private static final Logger log = LoggerFactory.getLogger(MockService.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private Faker faker;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInRoleRepository userInRoleRepository;

	public void mockRoles() {
		List<RoleEntity> roles = Arrays.asList(new RoleEntity("ADMIN"), new RoleEntity("SUPPORT"),
				new RoleEntity("USER"));
		roles.stream().forEach(role -> this.roleRepository.save(role));
	}

	public void mockUsers() {
		this.mockRoles();

		final int size = 5;
		for (int i = 0; i < size; i++) {
			final UserEntity user = new UserEntity();
			user.setUsername(this.faker.name().username());
			user.setPassword(this.faker.dragonBall().character());
			final UserEntity userDB = this.userRepository.save(user);

			int index = new Random().nextInt(3);
			RoleEntity roleDB = this.roleRepository.findById(++index).get();
			UserInRoleEntity userInRole = new UserInRoleEntity(userDB, roleDB);
			this.userInRoleRepository.save(userInRole);
			log.info("User created with username '{}', password '{}' and role '{}'", userDB.getUsername(),
					userDB.getPassword(), userInRole.getRole().getName());
		}
	}

}
