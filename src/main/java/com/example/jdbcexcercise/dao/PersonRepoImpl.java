package com.example.jdbcexcercise.dao;

import com.example.jdbcexcercise.model.Person;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository @Slf4j @RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonRepoImpl implements PersonRepo {
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query("select * from person", ROW_MAPPER);
    }

    @Override
    public Person findById(String id) {
        Person person = null;
        try{
            person = jdbcTemplate.queryForObject("select * from person where id = ?", ROW_MAPPER, id);
        } catch (DataAccessException dataAccessException){
            log.error("Entity with id: {} not found", id);
        }
        return person;
    }

    @Override
    public Person save(Person person) {
        if(person.getId()==null){
            person.setId(UUID.randomUUID().toString());

        jdbcTemplate.update("insert into person values (?, ?, ?)", person.getId(), person.getName(), person.getEmail()); }
        else {
            jdbcTemplate.update("update person set name = ?2, email = ?3 where id = ?1",  person.getId(), person.getName(), person.getEmail());
        }
        return findById(person.getId());
    }

    @Override
    public int delete(String id) {
        return jdbcTemplate.update("delete from person where id = ?", id);
    }
}
