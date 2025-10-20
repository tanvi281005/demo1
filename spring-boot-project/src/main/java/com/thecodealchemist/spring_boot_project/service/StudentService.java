package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.model.Student;
import com.thecodealchemist.spring_boot_project.model.TransactionViewDTO;
import com.thecodealchemist.spring_boot_project.dao.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

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
            int age = java.time.Period.between(student.getDob(), LocalDate.now()).getYears();
            student.setAge(age);
        }

        // Default wallet if null
        if (student.getWallet() == null) {
            student.setWallet(0.0f);
        }

        // Save student using JDBC repository
        studentRepository.save(student);
        return student;
    }

    // Authenticate student by email and dob
    public Student authenticate(String email, LocalDate dob) {
        return studentRepository.findByEmailAndDob(email, dob).orElse(null);
    }

    // Get student by email and dob
    public Student getStudentByEmailAndDob(String email, LocalDate dob) {
        return studentRepository.findByEmailAndDob(email, dob).orElse(null);
    }

    // Get student by ID
    public Student getStudentById(Integer studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    // Update student info
    public void updateStudent(Student student) throws Exception {
        if (student.getStudentId() == null) {
            throw new Exception("Student ID is required for update");
        }
        studentRepository.update(student);
    }

    public Student updateStudent2(Student student) {
    studentRepository.update(student);
    if (student.getStudentId() != null) {
        return studentRepository.findById2(student.getStudentId()); // return fresh copy
    } else {
        return null;
    }
}


}
