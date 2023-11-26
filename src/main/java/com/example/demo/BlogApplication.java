package com.example.demo;

import com.example.demo.models.Admin;
import com.example.demo.repo.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BlogApplication implements CommandLineRunner {
	private final AdminUserRepository adminUserRepository;
	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		adminUserRepository.save(new Admin(Long.valueOf("1"),
				"admin",
				"$2a$12$nuBnzdW4paCC64fWOAPeWO8RhB347YXhBVHrtE19CwxpJAQQtptTW\n",
				true));
	}
}