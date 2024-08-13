package com.atmosware.belatrix.managmentService;

import com.atmosware.belatrix.core.annotations.EnableSecurity;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import rpccommon.GrpcConfig;

@SpringBootApplication
@EnableSecurity
@Import(GrpcConfig.class)
public class ManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementServiceApplication.class, args);
	}
	@Bean
	public GrpcAuthenticationReader grpcAuthenticationReader(){
		return new BasicGrpcAuthenticationReader();
	}
}
