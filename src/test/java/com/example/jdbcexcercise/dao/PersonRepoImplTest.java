package com.example.jdbcexcercise.dao;

import com.example.jdbcexcercise.model.Person;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PersonRepoImplTest {
    EmbeddedDatabase embeddedDatabase;
    JdbcTemplate jdbcTemplate;
    PersonRepo personRepo;
    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        personRepo = new PersonRepoImpl(jdbcTemplate);

    }

    @After
    public void shutDown(){
        embeddedDatabase.shutdown();
    }

    @Test
    void findAll() {
        assertNotNull(personRepo.findAll());
        assertEquals(personRepo.findAll().size(), 2);
    }

    @Test
    void findById() {
        assertNotNull(personRepo.findById("pustota"));
        assertNull(personRepo.findById("nonExistingId"));
    }

    @Test
    void normalSave() {
        Person person = personRepo.save(new Person("giorgi", "giorgi@yahoo.com"));
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("giorgi", person.getName());
    }

    @Test
    void invalidSave(){
        assertThrows(DataIntegrityViolationException.class,()-> personRepo.save(new Person()));
    }

    @Test
    void testIdConflict(){
        assertThrows(DataIntegrityViolationException.class, ()->personRepo.save(new Person("luka", "zura.@gmail.com")));
    }
    @Test
    void testUpdate(){
        Person person = jdbcTemplate.queryForObject("select * from person where id ='pustota'", personRepo.ROW_MAPPER);
        person.setName("Zurab");
        person = personRepo.save(person);
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Zurab", person.getName());
    }
    @Test
    void updateConflict(){
        Person person = jdbcTemplate.queryForObject("select * from person where id = 'pustota'", personRepo.ROW_MAPPER);
        person.setEmail("qucu@mail.ru");
        assertThrows(DataIntegrityViolationException.class, ()->personRepo.save(person));

    }
    @Test
    void updateNull(){
        Person person = jdbcTemplate.queryForObject("select * from person where id = 'pustota'", personRepo.ROW_MAPPER);
        person.setName(null);
        assertThrows(DataIntegrityViolationException.class, ()->personRepo.save(person));

    }

    @Test
    void delete() {
        assertEquals(1, personRepo.delete("pustota"));
        assertEquals(0, personRepo.delete("noExistingID"));
    }

}