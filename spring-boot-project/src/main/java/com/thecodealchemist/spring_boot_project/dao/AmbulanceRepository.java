package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.Ambulance;
import com.thecodealchemist.spring_boot_project.model.Services;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AmbulanceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ServiceRepository serviceRepository;

    public AmbulanceRepository(JdbcTemplate jdbcTemplate, ServiceRepository serviceRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.serviceRepository = serviceRepository;
    }

    private final RowMapper<Ambulance> ambulanceRowMapper = new RowMapper<>() {
        @Override
        public Ambulance mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ambulance ambulance = new Ambulance();
            Integer serviceId = rs.getInt("service_id");
            ambulance.setServiceId(serviceId);
            ambulance.setService(serviceRepository.findById(serviceId).orElse(null));
            ambulance.setFromPos(rs.getString("from_pos"));
            ambulance.setToPos(rs.getString("to_pos"));
            ambulance.setPatientCondition(rs.getString("patient_condition"));
            ambulance.setAmbulanceNo(rs.getString("ambulance_no"));
            ambulance.setNurseRequired(rs.getBoolean("nurse_required"));
            ambulance.setNecessity(rs.getString("necessity"));
            return ambulance;
        }
    };

    // Save
    public int save(Ambulance ambulance) {
        String sql = "INSERT INTO Ambulance (service_id, from_pos, to_pos, patient_condition, ambulance_no, nurse_required, necessity) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                ambulance.getService().getServiceId(),
                ambulance.getFromPos(),
                ambulance.getToPos(),
                ambulance.getPatientCondition(),
                ambulance.getAmbulanceNo(),
                ambulance.getNurseRequired(),
                ambulance.getNecessity()
        );

        Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        ambulance.setServiceId(id);
        return id;
    }

    // Find by ID
    public Optional<Ambulance> findById(Integer serviceId) {
        String sql = "SELECT * FROM Ambulance WHERE service_id = ?";
        try {
            Ambulance ambulance = jdbcTemplate.queryForObject(sql, ambulanceRowMapper, serviceId);
            return Optional.of(ambulance);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Find all
    public List<Ambulance> findAll() {
        String sql = "SELECT * FROM Ambulance";
        return jdbcTemplate.query(sql, ambulanceRowMapper);
    }

    // Update
    public void update(Ambulance ambulance) {
        String sql = "UPDATE Ambulance SET from_pos=?, to_pos=?, patient_condition=?, ambulance_no=?, nurse_required=?, necessity=? " +
                     "WHERE service_id=?";
        jdbcTemplate.update(sql,
                ambulance.getFromPos(),
                ambulance.getToPos(),
                ambulance.getPatientCondition(),
                ambulance.getAmbulanceNo(),
                ambulance.getNurseRequired(),
                ambulance.getNecessity(),
                ambulance.getServiceId()
        );
    }
    
    public void deleteById(Integer serviceId) {
        String sql = "DELETE FROM Ambulance WHERE service_id=?";
        jdbcTemplate.update(sql, serviceId);
    }
}
