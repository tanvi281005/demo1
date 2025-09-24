package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import com.thecodealchemist.spring_boot_project.model.Request;
import com.thecodealchemist.spring_boot_project.model.Services;
import com.thecodealchemist.spring_boot_project.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class MarketTransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public MarketTransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<MarketTransaction> rowMapper = (rs, rowNum) -> {
        MarketTransaction t = new MarketTransaction();
        t.setServiceId(rs.getInt("service_id"));
        t.setItemId(rs.getInt("item_id"));
        t.setIsApproved(rs.getBoolean("is_approved"));
        t.setNegotiatedPrice(rs.getBigDecimal("negotiated_price"));
        t.setFinalPrice(rs.getBigDecimal("final_price"));
        t.setOriginalPrice(rs.getBigDecimal("original_price"));
        return t;
    };

    // ---------------------- NEW METHOD ----------------------
    @Transactional
    public MarketTransaction createTransaction(Student student, MarketTransaction transaction) {
        // 1️⃣ Insert a new service (auto-increment)
        String serviceSql = "INSERT INTO services(service_name) VALUES (?)";
        KeyHolder serviceKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(serviceSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Services.ServiceType.BUY_SELL.name());
            return ps;
        }, serviceKeyHolder);
        Integer serviceId = serviceKeyHolder.getKey().intValue();

        // 2️⃣ Insert a new request linked to the service
        String requestSql = "INSERT INTO request(student_id, service_id, status, created_at) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(requestSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, student.getStudentId());
            ps.setInt(2, serviceId);
            ps.setString(3, Request.Status.Pending.name());
            ps.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
            return ps;
        });

        // 3️⃣ Insert the market transaction
        String transactionSql = "INSERT INTO market_transaction(service_id, item_id, negotiated_price, final_price, original_price, is_approved) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(transactionSql,
                serviceId,
                transaction.getItemId(),
                transaction.getNegotiatedPrice(),
                transaction.getFinalPrice(),
                transaction.getOriginalPrice(),
                false // default approval
        );

        transaction.setServiceId(serviceId);
        return transaction;
    }

    // Update final price (by seller)
    public void updateFinalPrice(Integer serviceId, BigDecimal finalPrice) {
        String sql = "UPDATE market_transaction SET final_price = ? WHERE service_id = ?";
        jdbcTemplate.update(sql, finalPrice, serviceId);
    }

    // Approve transaction (by buyer)
    public void approveTransaction(Integer serviceId, Boolean isApproved) {
        String sql = "UPDATE market_transaction SET is_approved = ? WHERE service_id = ?";
        jdbcTemplate.update(sql, isApproved, serviceId);
    }

    // Get transactions
    public List<MarketTransaction> getAllTransactions() {
        return jdbcTemplate.query("SELECT * FROM market_transaction", rowMapper);
    }
}
