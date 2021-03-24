package com.example.demo.infrastructure;

import com.example.demo.domain.LibraryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLibraryEventRepository extends JpaRepository<LibraryEvent, Integer> {
}
