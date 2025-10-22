package com.thecodealchemist.spring_boot_project.controller;
import com.thecodealchemist.spring_boot_project.model.Student;
import com.thecodealchemist.spring_boot_project.model.TransactionViewDTO;
import com.thecodealchemist.spring_boot_project.service.StudentService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

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

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getDob() { return dob; }
        public void setDob(String dob) { this.dob = dob; }
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
            // set session attribute and extend session timeout for dev
            session.setAttribute("studentId", student.getStudentId());
            session.setMaxInactiveInterval(60 * 60); // 1 hour for testing

            // debug log: prints session id and student id
            System.out.println("[LOGIN] sessionId=" + session.getId() + " studentId=" + student.getStudentId());

            return ResponseEntity.ok("Login successful! Access granted.");
        } else {
            return ResponseEntity.status(401).body("Invalid email or date of birth.");
        }
    }

    // // Helper endpoint to quickly verify session presence & studentId
    // @GetMapping("/check-session")
    // public ResponseEntity<String> checkSession(HttpSession session) {
    //     Integer studentId = (Integer) session.getAttribute("studentId");
    //     String msg = "sessionId=" + session.getId() + " studentId=" + studentId;
    //     return studentId == null ? ResponseEntity.status(401).body(msg) : ResponseEntity.ok(msg);
    // }

    
    
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

    @PutMapping("/profile/update")
    public Student updateProfile(@RequestBody Student updatedStudent, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }

        // Ensure user only updates their own profile
        updatedStudent.setStudentId(studentId);

        return studentService.updateStudent2(updatedStudent);
    }

    // POST endpoint to logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // destroys the session
        return ResponseEntity.ok("Logout successful.");
    }

    @GetMapping("/user/buysellprofile")
    public ResponseEntity<Student> getBuySellprofile(HttpSession session){
        Integer studentId=(Integer) session.getAttribute("studentId");
        if(studentId==null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Student student=studentService.getStudentById(studentId);
        return student!= null? ResponseEntity.ok(student): ResponseEntity.notFound().build();
    }

   
}
