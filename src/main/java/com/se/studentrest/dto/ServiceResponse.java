package com.se.studentrest.dto;

import com.se.studentrest.entity.Student;

public class ServiceResponse {
    private String status;
    private Student student;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
