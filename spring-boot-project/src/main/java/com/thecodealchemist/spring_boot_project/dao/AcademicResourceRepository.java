package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.AcademicResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AcademicResourceRepository {

    private final JdbcTemplate jdbcTemplate;

    public AcademicResourceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AcademicResource> resourceMapper = (rs, rowNum) -> {
        AcademicResource res = new AcademicResource();
        res.setResourceId(rs.getInt("resource_id"));
        res.setStudentId(rs.getInt("user_id"));
        res.setCourse(rs.getString("course"));
        res.setResourceType(AcademicResource.ResourceType.valueOf(rs.getString("resource_type")));
        res.setUploadDate(rs.getTimestamp("upload_date").toLocalDateTime());
        res.setContent(rs.getBytes("content"));
        return res;
    };

    public void save(AcademicResource resource) {
        String sql = "INSERT INTO AcademicResource (user_id, course, resource_type, content) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                resource.getStudentId(),
                resource.getCourse(),
                resource.getResourceType().name(),
                resource.getContent());
    }

    public List<AcademicResource> findAllByStudentId(int studentId) {
        String sql = "SELECT * FROM AcademicResource WHERE user_id = ?";
        return jdbcTemplate.query(sql, resourceMapper, studentId);
    }

    public AcademicResource findById(int resourceId) {
        String sql = "SELECT * FROM AcademicResource WHERE resource_id = ?";
        return jdbcTemplate.queryForObject(sql, resourceMapper, resourceId);
    }
}
