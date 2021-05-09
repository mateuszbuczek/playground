package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FruitRepository extends CrudRepository<Fruit, Long> {

    Long countByName(String name);

    @Query(value = "select distinct name from Fruit", nativeQuery = true)
    List<String> findUniqueFruitNames();
}
