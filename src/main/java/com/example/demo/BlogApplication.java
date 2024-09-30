package com.example.demo;

import com.example.demo.models.Admin;
import com.example.demo.models.Language;
import com.example.demo.repo.AdminUserRepository;
import com.example.demo.repo.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableCaching
public class BlogApplication implements CommandLineRunner {
	private final AdminUserRepository adminUserRepository;
	private final LanguageRepository languageRepository;

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

		createLanguages();
	}

	public void createLanguages() {
		boolean ruExists = languageRepository.existsByCode("ru");
		boolean enExists = languageRepository.existsByCode("en");

		List<Language> langs = new ArrayList<>();

		if (!ruExists) {
			langs.add(Language.builder().name("Русский").code("ru").active(true).isDefault(true).build());
		}

		if (!enExists) {
			langs.add(
					Language.builder().name("English").code("en").active(true).isDefault(false).build());
		}

		if (!langs.isEmpty()) {
			languageRepository.saveAll(langs);
		}
	}
}