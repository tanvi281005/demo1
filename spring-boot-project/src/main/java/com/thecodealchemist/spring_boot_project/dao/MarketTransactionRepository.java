package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MarketTransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public MarketTransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<MarketTransaction> rowMapper = new RowMapper<>() {
        @Override
        public MarketTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            MarketTransaction t = new MarketTransaction();
            t.setServiceId(rs.getInt("service_id"));
            t.setItemId(rs.getInt("item_id"));
            t.setIsApproved(rs.getBoolean("is_approved"));
            t.setNegotiatedPrice(rs.getBigDecimal("negotiated_price"));
            t.setFinalPrice(rs.getBigDecimal("final_price"));
            t.setOriginalPrice(rs.getBigDecimal("original_price"));
            return t;
        }
    };

    // Create a new transaction
    public void createTransaction(MarketTransaction transaction) {
        String sql = "INSERT INTO market_transaction (service_id, item_id, is_approved, negotiated_price, final_price, original_price) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                transaction.getServiceId(),
                transaction.getItemId(),
                transaction.getIsApproved(),
                transaction.getNegotiatedPrice(),
                transaction.getFinalPrice(),
                transaction.getOriginalPrice()
        );
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


    // Get transactions for a buyer
    public List<MarketTransaction> getAllTransactions() {
        return jdbcTemplate.query("SELECT * FROM market_transaction", rowMapper);
    }
}
