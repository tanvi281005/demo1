package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CounsellingBookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 🔹 Step 1: Insert a new Service and return generated ID
    public int insertService(Services service) {
        String sql = "INSERT INTO service (service_name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getServiceName().toString());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    // 🔹 Step 2: Insert Request
    public int insertRequest(Request request) {
        String sql = "INSERT INTO request (student_id, service_id, created_at, status) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                request.getStudent().getStudentId(),
                request.getService().getServiceId(),
                Timestamp.valueOf(request.getCreatedAt()),
                request.getStatus().name()
        );
    }

    // 🔹 Step 3: Insert Counselling Session
    public int insertCounsellingSession(CounsellingSession session) {
        String sql = "INSERT INTO counsellingsession " +
                "(counsellor_id, service_id, is_approved, counselling_mode, timeapproved) " +
                "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, session.getCounsellorId());
            ps.setInt(2, session.getServiceId());
            ps.setBoolean(3, session.getIsApproved());
            ps.setString(4, session.getCounsellingMode().name());
            ps.setTime(5, session.gettimeapproved());
            return ps;
        });
    }

    // Fetch counselling session
public CounsellingSession findCounsellingSession(Integer serviceId, Integer counsellorId) {
    String sql = "SELECT * FROM counsellingsession WHERE service_id = ? AND counsellor_id = ?";
    return jdbcTemplate.query(sql, new Object[]{serviceId, counsellorId}, rs -> {
        if (rs.next()) {
            CounsellingSession session = new CounsellingSession();
            session.setServiceId(rs.getInt("service_id"));
            session.setCounsellorId(rs.getInt("counsellor_id"));
            session.setIsApproved(rs.getBoolean("is_approved"));
            session.setCounsellingMode(CounsellingSession.CounsellingMode.valueOf(rs.getString("counselling_mode")));
            session.settimeapproved(rs.getTime("timeapproved"));
            return session;
        }
        return null;
    });
}

// Update approval flag
public int updateCounsellingApproval(Integer serviceId, Integer counsellorId, boolean approved) {
    String sql = "UPDATE counsellingsession SET is_approved = ? WHERE service_id = ? AND counsellor_id = ?";
    return jdbcTemplate.update(sql, approved, serviceId, counsellorId);
}

// Update request status
public int updateRequestStatus(Integer serviceId, Request.Status status) {
    String sql = "UPDATE request SET status = ? WHERE service_id = ?";
    return jdbcTemplate.update(sql, status.name(), serviceId);
}

// Get student ID from service_id
public int getStudentIdByServiceId(Integer serviceId) {
    String sql = "SELECT student_id FROM request WHERE service_id = ?";
    return jdbcTemplate.queryForObject(sql, Integer.class, serviceId);
}

public List<Map<String, Object>> findPendingRequestsForCounsellor(Integer counsellorId) {
    String sql = """
        SELECT r.student_id, r.service_id, cs.timeapproved, cs.counselling_mode, s.first_name
        FROM request r
        JOIN counsellingsession cs ON r.service_id = cs.service_id
        JOIN student s ON r.student_id = s.student_id
        WHERE cs.counsellor_id = ? AND cs.is_approved = false AND r.status = 'Pending'
    """;

    return jdbcTemplate.query(sql, new Object[]{counsellorId}, (rs, rowNum) -> {
        Map<String, Object> map = new HashMap<>();
        map.put("student_id", rs.getInt("student_id"));
        map.put("student_name", rs.getString("first_name"));
        map.put("service_id", rs.getInt("service_id"));
        map.put("timeapproved", rs.getTime("timeapproved"));
        map.put("counselling_mode", rs.getString("counselling_mode"));
        return map;
    });
}



}
