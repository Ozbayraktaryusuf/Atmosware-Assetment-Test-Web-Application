package com.atmosware.belatrix.examSercvice;

import com.atmosware.belatrix.core.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSecurity
public class ExamSercviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamSercviceApplication.class, args);
	}

}
