package com.se.studentrest.controller;

import com.se.studentrest.dto.ServiceResponse;
import com.se.studentrest.entity.Student;
import com.se.studentrest.repository.StudentRepository;
import com.se.studentrest.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/student/{id}")
    public Student updateStudent(@RequestBody Student updatedRecord, @PathVariable Long id) {
        studentRepository.findById(id)
                .map(student -> { // update when id is found
                    student.setName(updatedRecord.getName());
                    student.setEmail(updatedRecord.getEmail());
                    student.setRole(updatedRecord.getRole());
                    return studentRepository.save(student);
                }).orElseGet(()-> { // if id is not found, create new one
                    updatedRecord.setId(id);
                    return studentRepository.save(updatedRecord);
                });
        return updatedRecord;
    }

    @PostMapping("/student/checkpassword")
    public ServiceResponse checkPassword(@RequestBody Student student) {
        Student studentRecord = new Student();
        final ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatus("False");
        serviceResponse.setStudent(studentRecord);

        if (student.getEmail() != null && student.getPassword() != null) {
            final String hashedPassword = SecurityUtils.hashPassword(student.getPassword());
            List<Student> students = studentRepository.findByEmailAndPassword(student.getEmail(), student.getPassword());
            if (students.size() > 0) {
                studentRecord = students.get(0);
                serviceResponse.setStudent(studentRecord);
                serviceResponse.setStatus("OK");
            }
        }
        return serviceResponse;
    }

}
