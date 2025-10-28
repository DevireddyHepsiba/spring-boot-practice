package restAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiMain {
    public static void main(String[] args) {
        SpringApplication.run(RestApiMain.class, args);
        System.out.println("✅ Spring Boot Application Started Successfully!");
    }
}
