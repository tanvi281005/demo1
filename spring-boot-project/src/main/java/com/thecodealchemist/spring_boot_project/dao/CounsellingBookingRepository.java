package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.CounsellingSession;
import com.thecodealchemist.spring_boot_project.model.Request;
import com.thecodealchemist.spring_boot_project.model.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

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
        return jdbcTemplate.update(sql,
                session.getCounsellorId(),
                session.getServiceId(),
                session.getIsApproved(),
                session.getCounsellingMode().name(),
                session.gettimeapproved()
        );
    }
}
