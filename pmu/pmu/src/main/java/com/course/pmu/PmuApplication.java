package com.course.pmu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Application Spring Boot
 *
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableSwagger2WebMvc
public class PmuApplication {
	public static void main(String[] args) {
		SpringApplication.run(PmuApplication.class, args);
	}
}
