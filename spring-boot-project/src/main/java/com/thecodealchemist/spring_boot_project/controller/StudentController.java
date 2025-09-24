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
    public static class LoginRequest {
    private String email;
    private String dob; // in YYYY-MM-DD
    
    public LoginRequest() {}

    public LoginRequest(String email, String dob) {
        this.email = email;
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    // getters & setters
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

 @PostMapping("/api/login")
public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest,
                                    HttpSession session) {
    LocalDate dateOfBirth;
    try {
        dateOfBirth = LocalDate.parse(loginRequest.getDob());
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Invalid date format. Use YYYY-MM-DD");
    }

    Student student = studentService.authenticate(loginRequest.getEmail(), dateOfBirth);

    if (student != null) {
        session.setAttribute("studentId", student.getStudentId());
        return ResponseEntity.ok("Login successful! Access granted.");
    } else {
        return ResponseEntity.status(401).body("Invalid email or date of birth.");
    }
}

@GetMapping("/profile")
public ResponseEntity<Student> getProfile(HttpSession session) {
    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Student student = studentService.getStudentById(studentId);
    if (student == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(student);
}


// POST endpoint to logout
@PostMapping("/logout")
public ResponseEntity<String> logout(HttpSession session) {
    session.invalidate(); // destroys the session
    return ResponseEntity.ok("Logout successful.");
}


}
