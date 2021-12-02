package com.se.studentrest.repository;

import com.se.studentrest.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByEmail(String email);
    List<Student> findByEmailAndPassword(String email, String password);
    List<Student> findByName(String name);


}
