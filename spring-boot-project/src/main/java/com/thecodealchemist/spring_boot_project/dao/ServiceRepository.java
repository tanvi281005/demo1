package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.Services;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class ServiceRepository {

    private final JdbcTemplate jdbcTemplate;

    public ServiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Services> serviceRowMapper = new RowMapper<>() {
        @Override
        public Services mapRow(ResultSet rs, int rowNum) throws SQLException {
            Services service = new Services();
            service.setServiceId(rs.getInt("service_id"));
            service.setServiceName(Services.ServiceType.valueOf(rs.getString("service_name")));
            return service;
        }
    };

    // Save a new service and return generated ID
    public int save(Services service) {
        String sql = "INSERT INTO Service (service_name) VALUES (?)";
        jdbcTemplate.update(sql, service.getServiceName().name());

        // Retrieve generated ID
        Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        service.setServiceId(id);
        return id;
    }

    // Find service by ID
    public Optional<Services> findById(Integer id) {
        String sql = "SELECT * FROM Service WHERE service_id = ?";
        try {
            Services service = jdbcTemplate.queryForObject(sql, serviceRowMapper, id);
            return Optional.of(service);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Find service by name
    public Optional<Services> findByServiceName(Services.ServiceType serviceName) {
        String sql = "SELECT * FROM Service WHERE service_name = ?";
        try {
            Services service = jdbcTemplate.queryForObject(sql, serviceRowMapper, serviceName.name());
            return Optional.of(service);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Update service
    public void update(Services service) {
        String sql = "UPDATE Service SET service_name = ? WHERE service_id = ?";
        jdbcTemplate.update(sql, service.getServiceName().name(), service.getServiceId());
    }

    // Delete service
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Service WHERE service_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
