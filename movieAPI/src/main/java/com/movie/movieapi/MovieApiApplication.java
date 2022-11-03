package com.movie.movieapi;

import com.movie.movieapi.entity.User;
import com.movie.movieapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MovieApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieApiApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder)
//	{
//		return args -> {
//			users.save(new User("admin","user2",encoder.encode("admin")));
//		};
//
//	}

}