package com.example.jdbcexcercise.dao;

import com.example.jdbcexcercise.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;


public interface PersonRepo {
    RowMapper<Person> ROW_MAPPER = (ResultSet resultSet, int rowNum)->
            new Person(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("email"));

    List<Person> findAll();
    Person findById(String id);
    Person save(Person person);
    int delete(String id);
}
