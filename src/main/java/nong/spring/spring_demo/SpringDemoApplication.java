package nong.spring.spring_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDemoApplication {
	private static Logger logger = LoggerFactory.getLogger("SpringDemoApplication");
	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}
}
