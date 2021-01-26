package br.com.goodha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class GodhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GodhaApplication.class, args);
	}

}
