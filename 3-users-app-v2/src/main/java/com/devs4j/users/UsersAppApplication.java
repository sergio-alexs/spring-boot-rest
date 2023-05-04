package com.devs4j.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devs4j.users.services.MockService;

@SpringBootApplication
public class UsersAppApplication implements ApplicationRunner {

	@Autowired
	private MockService mockService;

	public static void main(final String[] args) {
		SpringApplication.run(UsersAppApplication.class, args);
	}

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		this.mockService.mockUsers();
	}

}
