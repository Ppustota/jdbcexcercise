package com.example.jdbcexcercise;

import com.example.jdbcexcercise.dao.PersonRepo;
import com.example.jdbcexcercise.model.Person;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class JdbcexcerciseApplication {

    @SneakyThrows
    public static void main(String[] args) {

        SpringApplication.run(JdbcexcerciseApplication.class, args);


    }

    @Bean
    CommandLineRunner runner(PersonRepo personRepo){
        return args -> {
          personRepo.save(new Person("Giorgi", "Giorgi@email.com"));
        };
    }

}
