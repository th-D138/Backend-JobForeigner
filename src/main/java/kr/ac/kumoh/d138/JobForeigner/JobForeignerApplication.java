package kr.ac.kumoh.d138.JobForeigner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class JobForeignerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobForeignerApplication.class, args);
	}

}
