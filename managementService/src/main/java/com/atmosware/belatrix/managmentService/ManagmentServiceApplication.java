package com.atmosware.belatrix.managmentService;

import com.atmosware.belatrix.core.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSecurity
public class ManagmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagmentServiceApplication.class, args);
	}

}
