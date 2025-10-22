package com.thecodealchemist.spring_boot_project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.thecodealchemist.spring_boot_project.model.Counsellor;
import com.thecodealchemist.spring_boot_project.model.Counsellor.Specialization;
import java.util.*;
@Repository
public class CounsellorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Counsellor mapRowToCounsellor(ResultSet rs, int rowNum) throws SQLException {
        Counsellor counsellor = new Counsellor();
        counsellor.setCounsellorId(rs.getInt("counsellor_id"));
        counsellor.setSpecialization(Counsellor.Specialization.valueOf(
            rs.getString("specialization").replace(" ", "")
        ));
        counsellor.setNoOfStudentsCounselled(rs.getInt("no_of_students_counselled"));
        counsellor.setSelfDescription(rs.getString("self_description"));
        counsellor.setRating(rs.getDouble("rating"));
        counsellor.setJoinedAt(rs.getTimestamp("joined_at"));
        return counsellor;
    }

    public List<Counsellor> findAllCounsellors() {
        String sql = "SELECT * FROM counsellor";
        return jdbcTemplate.query(sql, this::mapRowToCounsellor);
    }

    public List<Counsellor> findByCategory(Specialization category) {
        String sql = "SELECT * FROM counsellor WHERE specialization = ?";
        return jdbcTemplate.query(sql, this::mapRowToCounsellor, category.name().replace("_", " "));
    }
}
