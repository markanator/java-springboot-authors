package com.ambro.authors;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class AuthorsApplication {


	public static void main(String[] args) {
		SpringApplication.run(AuthorsApplication.class, args);
	}



}
