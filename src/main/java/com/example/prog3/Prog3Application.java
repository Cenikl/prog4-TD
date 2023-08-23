package com.example.prog3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Prog3Application {
	public static void main(String[] args) {
		SpringApplication.run(Prog3Application.class, args);
	}
}
