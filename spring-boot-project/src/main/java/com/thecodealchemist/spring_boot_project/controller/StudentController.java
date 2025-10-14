package com.thecodealchemist.spring_boot_project.controller;
import com.thecodealchemist.spring_boot_project.model.Student;
import com.thecodealchemist.spring_boot_project.service.StudentService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

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
        private String dob;

        public LoginRequest() {}
        public LoginRequest(String email, String dob) { this.email = email; this.dob = dob; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getDob() { return dob; }
        public void setDob(String dob) { this.dob = dob; }
    }

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
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        LocalDate dob;
        try { dob = LocalDate.parse(loginRequest.getDob()); } 
        catch (Exception e) { return ResponseEntity.badRequest().body("Invalid date format."); }

        Student student = studentService.authenticate(loginRequest.getEmail(), dob);
        if (student != null) {
            session.setAttribute("studentId", student.getStudentId());
            System.out.println("Login successful: studentId=" + student.getStudentId());
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid email or date of birth.");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Student> getProfile(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Student student = studentService.getStudentById(studentId);
        if (student == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @PutMapping("/profile/update")
    public Student updateProfile(@RequestBody Student updatedStudent, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        updatedStudent.setStudentId(studentId);
        return studentService.updateStudent2(updatedStudent);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful.");
    }
}
