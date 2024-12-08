package org.team.defee;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.team.defee.common.util.JwtUtil;

@SpringBootApplication
public class DefeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DefeeApplication.class, args);
	}

}
