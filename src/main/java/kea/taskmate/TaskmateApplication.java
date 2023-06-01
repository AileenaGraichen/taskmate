package kea.taskmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) //excludes security login page in order to bypass
public class TaskmateApplication {
    public static void main(String[] args) {SpringApplication.run(TaskmateApplication.class, args);}

}
