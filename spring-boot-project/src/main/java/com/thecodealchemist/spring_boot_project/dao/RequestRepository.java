package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.Request;
import com.thecodealchemist.spring_boot_project.model.Student;
import com.thecodealchemist.spring_boot_project.model.Services;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class RequestRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StudentRepository studentRepository;
    private final ServiceRepository serviceRepository;

    public RequestRepository(JdbcTemplate jdbcTemplate,
                             StudentRepository studentRepository,
                             ServiceRepository serviceRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRepository = studentRepository;
        this.serviceRepository = serviceRepository;
    }

    private final RowMapper<Request> requestRowMapper = new RowMapper<>() {
        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
            Request request = new Request();
            request.setRequestId(rs.getInt("request_id"));
            Integer studentId = rs.getInt("student_id");
            Integer serviceId = rs.getInt("service_id");
            request.setStudent(studentRepository.findById(studentId).orElse(null));
            request.setService(serviceRepository.findById(serviceId).orElse(null));
            request.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            if (rs.getTimestamp("finished_at") != null)
                request.setFinishedAt(rs.getTimestamp("finished_at").toLocalDateTime());
            if (rs.getTime("duration") != null)
                request.setDuration(rs.getTime("duration").toLocalTime());
            request.setStatus(Request.Status.valueOf(rs.getString("status")));
            return request;
        }
    };

    // Save request
    public int save(Request request) {
        String sql = "INSERT INTO Request (student_id, service_id, created_at, finished_at, duration, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                request.getStudent().getStudentId(),
                request.getService().getServiceId(),
                request.getCreatedAt(),
                request.getFinishedAt(),
                request.getDuration(),
                request.getStatus().name()
        );

        Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        request.setRequestId(id);
        return id;
    }

    // Find by ID
    public Optional<Request> findById(Integer requestId) {
        String sql = "SELECT * FROM Request WHERE request_id = ?";
        try {
            Request request = jdbcTemplate.queryForObject(sql, requestRowMapper, requestId);
            return Optional.of(request);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Find all requests
    public List<Request> findAll() {
        String sql = "SELECT * FROM Request";
        return jdbcTemplate.query(sql, requestRowMapper);
    }

    // Update
    public void update(Request request) {
        String sql = "UPDATE Request SET student_id=?, service_id=?, created_at=?, finished_at=?, duration=?, status=? " +
                     "WHERE request_id=?";
        jdbcTemplate.update(sql,
                request.getStudent().getStudentId(),
                request.getService().getServiceId(),
                request.getCreatedAt(),
                request.getFinishedAt(),
                request.getDuration(),
                request.getStatus().name(),
                request.getRequestId()
        );
    }

    // Delete
    public void deleteById(Integer requestId) {
        String sql = "DELETE FROM Request WHERE request_id=?";
        jdbcTemplate.update(sql, requestId);
    }
}
