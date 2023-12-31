package com.example.demo;

import com.example.demo.models.Admin;
import com.example.demo.repo.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@RequiredArgsConstructor
@EnableCaching
public class BlogApplication implements CommandLineRunner {
	private final AdminUserRepository adminUserRepository;
	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Boolean adminExists = adminUserRepository.existsByUsername("admin");

		if(!adminExists) {
			adminUserRepository.save(new Admin(Long.valueOf("1"),
					"admin",
					"$2a$12$DLgJZNC6smbEwA5SDQxLqObQxrngqaejIIcCcSnFzKyf/pYZisjP.",
					true));
		}
	}
}