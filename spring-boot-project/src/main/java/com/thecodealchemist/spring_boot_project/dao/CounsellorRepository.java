package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.Counsellor;
import com.thecodealchemist.spring_boot_project.model.CounsellorTimings;
import com.thecodealchemist.spring_boot_project.model.CounsellingSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CounsellorRepository {

    private final JdbcTemplate jdbcTemplate;

    public CounsellorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Fetch counsellor basic info
    public Counsellor findById(int counsellorId) {
        String sql = "SELECT * FROM Counsellor WHERE counsellor_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{counsellorId}, new CounsellorRowMapper());
    }

    // Fetch available time slots
    public List<CounsellorTimings> findAvailableTimings(int counsellorId) {
        String sql = "SELECT * FROM CounsellorTimings WHERE counsellor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{counsellorId}, new CounsellorTimingRowMapper());
    }

    // Fetch counselling session details
    public List<CounsellingSession> findSessionsByCounsellor(int counsellorId) {
        String sql = "SELECT * FROM CounsellingSession WHERE counsellor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{counsellorId}, new CounsellingSessionRowMapper());
    }

    // Mappers
    private static class CounsellorRowMapper implements RowMapper<Counsellor> {
        @Override
        public Counsellor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Counsellor c = new Counsellor();
            c.setCounsellorId(rs.getInt("counsellor_id"));
            c.setSpecialization(Counsellor.Specialization.valueOf(rs.getString("specialization")));
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
