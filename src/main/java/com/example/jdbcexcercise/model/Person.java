package com.example.jdbcexcercise.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data @AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    String id;
    String name;
    String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
