package com.devs4j.users.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devs4j.users.entities.UserEntity;
import com.devs4j.users.entities.UserInRoleEntity;
import com.devs4j.users.repositories.UserInRoleRepository;
import com.devs4j.users.repositories.UserRepository;

/**
 * Service Spring Security Internal to manager db security.
 *
 * @author jroldan
 * @version 1.0
 * @since 23/01/30
 * @category Service
 */
@Service
public class SecurityDBService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserInRoleRepository userInRoleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> optional = userRepo.findByUsername(username);
		if (optional.isPresent()) {
			UserEntity userDB = optional.get();
			List<UserInRoleEntity> userInRolesDB = userInRoleRepo.findByUser(userDB);
			String[] roles = userInRolesDB.stream().map(r -> r.getRole().getName()).toArray(String[]::new);
			return User.withUsername(userDB.getUsername()).password(this.passwordEncoder.encode(userDB.getPassword()))
					.roles(roles).build();
		} else {
			throw new UsernameNotFoundException("User with username '" + username + "' not found");
		}
	}

}
