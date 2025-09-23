package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.Student;
import com.thecodealchemist.spring_boot_project.service.StudentService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // POST mapping for /register
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.registerStudent(student);
            return ResponseEntity.ok(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

        // LOGIN endpoint
    @PostMapping("/login")
public ResponseEntity<String> login(@RequestParam String email, 
                                    @RequestParam String dob, 
                                    HttpSession session) {

    LocalDate dateOfBirth;
    try {
        dateOfBirth = LocalDate.parse(dob);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Invalid date format. Use YYYY-MM-DD");
    }

    Student student = studentService.authenticate(email, dateOfBirth);

    if (student != null) {
        session.setAttribute("studentId", student.getStudentId());
        return ResponseEntity.ok("Login successful! Access granted.");
    } else {
        return ResponseEntity.status(401).body("Invalid email or date of birth.");
    }
}


}
