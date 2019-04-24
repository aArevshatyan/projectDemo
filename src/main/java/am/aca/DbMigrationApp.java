package am.aca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point
 */
@SpringBootApplication
public class DbMigrationApp {
    public static void main(String[] args) {
        SpringApplication.run(DbMigrationApp.class, args);
    }
}