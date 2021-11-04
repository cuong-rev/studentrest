package com.se.studentrest.controller;

import com.se.studentrest.entity.Student;
import com.se.studentrest.repository.StudentRepository;
import com.se.studentrest.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/student")
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @PostMapping("/student")
    public String saveStudent(@RequestBody Student student) {
        String password = student.getPassword();

        // Hash the password
        String hashedString = SecurityUtils.hashPassword(password);
        student.setPassword(hashedString);
        studentRepository.save(student);
        return "Success";
    }

}
