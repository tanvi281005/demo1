package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.Counsellor;
import com.thecodealchemist.spring_boot_project.model.CounsellorTimings;
import com.thecodealchemist.spring_boot_project.model.CounsellingSession;
import com.thecodealchemist.spring_boot_project.model.Counsellor.Specialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CounsellorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- Fetch methods ---
    public Counsellor findById(int counsellorId) {
        String sql = "SELECT * FROM Counsellor WHERE counsellor_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{counsellorId}, new CounsellorRowMapper());
    }

    public List<Counsellor> findAllCounsellors() {
        String sql = "SELECT * FROM Counsellor";
        return jdbcTemplate.query(sql, new CounsellorRowMapper());
    }

    public List<Counsellor> findByCategory(Specialization category) {
        String sql = "SELECT * FROM Counsellor WHERE specialization = ?";
        return jdbcTemplate.query(sql, new CounsellorRowMapper(), category.name().replace("_", " "));
    }

    public List<CounsellorTimings> findAvailableTimings(int counsellorId) {
        String sql = "SELECT * FROM CounsellorTimings WHERE counsellor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{counsellorId}, new CounsellorTimingRowMapper());
    }

    public List<CounsellingSession> findSessionsByCounsellor(int counsellorId) {
        String sql = "SELECT * FROM CounsellingSession WHERE counsellor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{counsellorId}, new CounsellingSessionRowMapper());
    }

    // --- Save methods ---
    public void saveCounsellor(Counsellor counsellor, int counsellorId) {
    String sql = "INSERT INTO Counsellor " +
                 "(counsellor_id, specialization, self_description, no_of_students_counselled, rating, joined_at) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";

    jdbcTemplate.update(sql,
        counsellorId,
        counsellor.getSpecialization().name(),
        counsellor.getSelfDescription(),
        counsellor.getNoOfStudentsCounselled(),
        counsellor.getRating(),
        counsellor.getJoinedAt()
    );
}
public void saveCounsellorWithId(Counsellor counsellor) {
    String sql = "INSERT INTO Counsellor (counsellor_id, specialization, self_description, no_of_students_counselled, rating, joined_at) VALUES (?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql,
            counsellor.getCounsellorId(),
            counsellor.getSpecialization().name(),
            counsellor.getSelfDescription(),
            counsellor.getNoOfStudentsCounselled(),
            counsellor.getRating(),
            counsellor.getJoinedAt());
}


    public void saveTiming(CounsellorTimings timing) {
        String sql = "INSERT INTO CounsellorTimings (counsellor_id, timing) VALUES (?, ?)";
        jdbcTemplate.update(sql, timing.getCounsellorId(), timing.getTiming());
    }

    // --- RowMappers ---
    private static class CounsellorRowMapper implements RowMapper<Counsellor> {
        @Override
        public Counsellor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Counsellor c = new Counsellor();
            c.setCounsellorId(rs.getInt("counsellor_id"));
            c.setSpecialization(Counsellor.Specialization.valueOf(
                rs.getString("specialization")
                    .replace(" ", "")
                    .replace("&", "And")
                    .replace("-", "")
            ));
            c.setNoOfStudentsCounselled(rs.getInt("no_of_students_counselled"));
            c.setSelfDescription(rs.getString("self_description"));
            c.setRating(rs.getDouble("rating"));
            c.setJoinedAt(rs.getTimestamp("joined_at"));
            return c;
        }
    }

    private static class CounsellorTimingRowMapper implements RowMapper<CounsellorTimings> {
        @Override
        public CounsellorTimings mapRow(ResultSet rs, int rowNum) throws SQLException {
            CounsellorTimings t = new CounsellorTimings();
            t.setCounsellorId(rs.getInt("counsellor_id"));
            t.setTiming(rs.getTime("timing").toLocalTime());
            return t;
        }
    }

    private static class CounsellingSessionRowMapper implements RowMapper<CounsellingSession> {
        @Override
        public CounsellingSession mapRow(ResultSet rs, int rowNum) throws SQLException {
            CounsellingSession s = new CounsellingSession();
            s.setCounsellorId(rs.getInt("counsellor_id"));
            s.setServiceId(rs.getInt("service_id"));
            s.setIsApproved(rs.getBoolean("is_approved"));
            s.setTimeOptions(rs.getString("time_options"));
            s.setCounsellingMode(CounsellingSession.CounsellingMode.valueOf(rs.getString("counselling_mode")));
            s.setFinalTime(rs.getTimestamp("final_time"));
            return s;
        }
    }
}
