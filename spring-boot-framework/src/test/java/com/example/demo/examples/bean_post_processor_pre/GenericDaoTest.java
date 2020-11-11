package com.example.demo.examples.bean_post_processor_pre;

import com.example.demo.examples.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GenericDaoTest {

    @DataAccess(entity = Person.class)
    private GenericDao<Person> personGenericDao;

    @Test
    public void isSingleton() {
        personGenericDao.findAll();
        assertEquals("Would create findAll query from Person", personGenericDao.getMessage());
    }
}