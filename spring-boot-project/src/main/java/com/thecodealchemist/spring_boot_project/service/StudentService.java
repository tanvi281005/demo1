package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.model.Student;
import com.thecodealchemist.spring_boot_project.dao.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Register new student
    public Student registerStudent(Student student) throws Exception {
        // Validate email and phone uniqueness
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new Exception("Email already registered");
        }
        if (studentRepository.existsByPhone(student.getPhone())) {
            throw new Exception("Phone number already registered");
        }

        // Validate year
        if (student.getYear() != null && (student.getYear() < 1 || student.getYear() > 5)) {
            throw new Exception("Year must be between 1 and 5");
        }

        // Optional: calculate age from DOB if not provided
        if (student.getDob() != null && student.getAge() == null) {
            int age = java.time.Period.between(student.getDob(), java.time.LocalDate.now()).getYears();
            student.setAge(age);
        }

        // Default wallet if null
        if (student.getWallet() == null) {
            student.setWallet(0.0f);
        }

        return studentRepository.save(student);
    }
}

