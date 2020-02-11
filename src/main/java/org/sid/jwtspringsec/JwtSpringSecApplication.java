package org.sid.jwtspringsec;

import org.sid.jwtspringsec.entity.AppRole;
import org.sid.jwtspringsec.entity.AppUser;
import org.sid.jwtspringsec.entity.Task;
import org.sid.jwtspringsec.repository.TaskRepository;
import org.sid.jwtspringsec.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class JwtSpringSecApplication implements CommandLineRunner {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(JwtSpringSecApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("user1", "user2", "user3", "admin").forEach(user -> {
            accountService.saveUser(user, "1234", "1234");
        });

        accountService.saveRole(new AppRole(null, "ADMIN"));
        accountService.saveRole(new AppRole(null, "USER"));

        accountService.addRoleToUse("admin", "ADMIN");
        accountService.addRoleToUse("admin", "USER");

        Stream.of("user1", "user2", "user3").forEach(u -> {
            accountService.addRoleToUse(u, "USER");
        });

        Stream.of("T1", "T2", "T3").forEach(task -> {
            taskRepository.save(new Task(null, task));
        });
        taskRepository.findAll().forEach(System.out::println);
    }

    @Bean
    BCryptPasswordEncoder getBCPE(){
        return new BCryptPasswordEncoder();
    }
}
