package kz.pak.studentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class StudentApiApplication {
	private static final Logger log = LoggerFactory.getLogger(StudentApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StudentApiApplication.class, args);
		log.info("Student API for Stanislav Pak is ready");
	}

}
