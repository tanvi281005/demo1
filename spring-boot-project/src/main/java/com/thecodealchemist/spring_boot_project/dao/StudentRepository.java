package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to convert ResultSet to Student object
    private static class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setStudentId(rs.getInt("student_id"));
            student.setFirstName(rs.getString("first_name"));
            student.setMiddleName(rs.getString("middle_name"));
            student.setLastName(rs.getString("last_name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            student.setYear(rs.getInt("year"));
            student.setBranch(rs.getString("branch"));
            String genderStr = rs.getString("gender");
            student.setGender(genderStr != null ? Student.Gender.valueOf(genderStr) : null);
            student.setHostel(rs.getString("hostel"));
            student.setGuardianName(rs.getString("guardian_name"));
            student.setGuardianNumber(rs.getString("guardian_number"));
            student.setWallet(rs.getFloat("wallet"));
            java.sql.Date dob = rs.getDate("dob");
            student.setDob(dob != null ? dob.toLocalDate() : null);
            student.setAge(rs.getInt("age"));
            return student;
        }
    }

    // Check if email exists
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Student WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    // Check if phone exists
    public boolean existsByPhone(String phone) {
        String sql = "SELECT COUNT(*) FROM Student WHERE phone = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, phone);
        return count != null && count > 0;
    }

    // Find student by email and dob
    public Optional<Student> findByEmailAndDob(String email, LocalDate dob) {
        String sql = "SELECT * FROM Student WHERE email = ? AND dob = ?";
        List<Student> students = jdbcTemplate.query(sql, new StudentRowMapper(), email, java.sql.Date.valueOf(dob));
        return students.isEmpty() ? Optional.empty() : Optional.of(students.get(0));
    }

    // Save a new student
    public void save(Student student) {
        String sql = "INSERT INTO Student (first_name, middle_name, last_name, email, phone, year, branch, gender, hostel, " +
                "guardian_name, guardian_number, wallet, dob, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                student.getFirstName(),
                student.getMiddleName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getYear(),
                student.getBranch(),
                student.getGender() != null ? student.getGender().name() : null,
                student.getHostel(),
                student.getGuardianName(),
                student.getGuardianNumber(),
                student.getWallet(),
                student.getDob() != null ? java.sql.Date.valueOf(student.getDob()) : null,
                student.getAge()
        );
    }

    // Find by ID
    public Optional<Student> findById(Integer id) {
        String sql = "SELECT * FROM Student WHERE student_id = ?";
        List<Student> students = jdbcTemplate.query(sql, new StudentRowMapper(), id);
        return students.isEmpty() ? Optional.empty() : Optional.of(students.get(0));
    }

    // Update student (optional)
    public void update(Student student) {
        String sql = "UPDATE Student SET first_name=?, middle_name=?, last_name=?, email=?, phone=?, year=?, branch=?, gender=?," +
                " hostel=?, guardian_name=?, guardian_number=?, wallet=?, dob=?, age=? WHERE student_id=?";
        jdbcTemplate.update(sql,
                student.getFirstName(),
                student.getMiddleName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getYear(),
                student.getBranch(),
                student.getGender() != null ? student.getGender().name() : null,
                student.getHostel(),
                student.getGuardianName(),
                student.getGuardianNumber(),
                student.getWallet(),
                student.getDob() != null ? java.sql.Date.valueOf(student.getDob()) : null,
                student.getAge(),
                student.getStudentId()
        );
    }

    public Student findById2(Integer id) {
    String sql = "SELECT * FROM Student WHERE student_id = ?";
    List<Student> students = jdbcTemplate.query(sql, new StudentRowMapper(), id);
    return students.isEmpty() ? null : students.get(0);
}

    
}
