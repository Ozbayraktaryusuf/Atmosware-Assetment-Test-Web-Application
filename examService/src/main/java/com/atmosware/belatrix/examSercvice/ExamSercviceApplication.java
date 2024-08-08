package com.atmosware.belatrix.examSercvice;

import com.atmosware.belatrix.core.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import rpccommon.GrpcConfig;

@SpringBootApplication
@EnableSecurity
@Import(GrpcConfig.class)
public class ExamSercviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamSercviceApplication.class, args);
	}

}
