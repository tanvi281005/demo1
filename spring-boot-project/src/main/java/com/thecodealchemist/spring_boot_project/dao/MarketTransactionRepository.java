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

    // Create dynamic Service row
    public int createService(String serviceName) {
        String sql = "INSERT INTO `Service` (service_name) VALUES (?)";
        jdbcTemplate.update(sql, serviceName);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    // Check if item exists
    public boolean itemExists(int itemId) {
        String sql = "SELECT COUNT(*) FROM `MarketItem` WHERE item_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, itemId);
        return count != null && count > 0;
    }

    // Create MarketTransaction (uses renamed table `market_transaction`)
    public void createMarketTransaction(int serviceId, int itemId, BigDecimal negotiatedPrice, BigDecimal originalPrice) {
        String sql = "INSERT INTO `market_transaction` " +
                     "(`service_id`, `item_id`, `is_approved`, `negotiated_price`, `final_price`, `original_price`) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, serviceId, itemId, 0, negotiatedPrice, BigDecimal.ZERO, originalPrice);
    }

    // Get all transactions for seller
    public List<MarketTransaction> findTransactionsBySeller(int sellerId) {
        String sql = "SELECT mt.service_id, mt.item_id, mt.negotiated_price, mt.final_price, mt.original_price, mt.is_approved " +
                     "FROM `market_transaction` mt " +
                     "JOIN `MarketItem` mi ON mt.item_id = mi.item_id " +
                     "WHERE mi.user_id = ?";
        return jdbcTemplate.query(sql, new RowMapper<MarketTransaction>() {
            @Override
            public MarketTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
                MarketTransaction mt = new MarketTransaction();
                mt.setServiceId(rs.getInt("service_id"));
                mt.setItemId(rs.getInt("item_id"));
                mt.setNegotiatedPrice(rs.getBigDecimal("negotiated_price"));
                mt.setFinalPrice(rs.getBigDecimal("final_price"));
                mt.setOriginalPrice(rs.getBigDecimal("original_price"));
                mt.setIsApproved(rs.getBoolean("is_approved"));
                return mt;
            }
        }, sellerId);
    }

    // Update final price
    public void updateFinalPrice(int serviceId, BigDecimal finalPrice) {
        String sql = "UPDATE `market_transaction` SET final_price = ? WHERE service_id = ?";
        jdbcTemplate.update(sql, finalPrice, serviceId);
    }

    // Approve transaction
    public void approveTransaction(int serviceId, boolean isApproved) {
        String sql = "UPDATE `market_transaction` SET is_approved = ? WHERE service_id = ?";
        jdbcTemplate.update(sql, isApproved ? 1 : 0, serviceId);
    }
}
