package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.TransactionViewDTO;
import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public int createService(String serviceName) {
        String sql = "INSERT INTO Service (service_name) VALUES (?)";
        jdbcTemplate.update(sql, serviceName);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public void createMarketTransaction(int serviceId, int itemId, BigDecimal negotiatedPrice,
            BigDecimal originalPrice) {
        String sql = """
                    INSERT INTO market_transaction (service_id, item_id, is_approved, negotiated_price, final_price, original_price)
                    VALUES (?, ?, null, ?, 0, ?)
                """;
        jdbcTemplate.update(sql, serviceId, itemId, negotiatedPrice, originalPrice);
    }

    public void createRequest(int studentId, int serviceId) {
        String sql = "INSERT INTO Request (student_id, service_id, status) VALUES (?, ?, 'Pending')";
        jdbcTemplate.update(sql, studentId, serviceId);
    }

    private TransactionViewDTO mapRowToDTO(ResultSet rs, int rowNum) throws SQLException {
        Integer serviceId = rs.getInt("service_id");
        Integer itemId = rs.getInt("item_id");
        BigDecimal negotiatedPrice = rs.getBigDecimal("negotiated_price");
        BigDecimal finalPrice = rs.getBigDecimal("final_price");
        BigDecimal originalPrice = rs.getBigDecimal("original_price");
        Boolean isApproved = (Boolean) rs.getObject("is_approved");
        String title = null;
        String buyerName = null;
        String sellerName = null;

        try {
            title = rs.getString("title");
        } catch (SQLException ignored) {
        }
        try {
            buyerName = rs.getString("buyer_name");
        } catch (SQLException ignored) {
        }
        try {
            sellerName = rs.getString("seller_name");
        } catch (SQLException ignored) {
        }

        return new TransactionViewDTO(serviceId, itemId,
                negotiatedPrice, finalPrice, originalPrice, isApproved,
                title, buyerName, sellerName);
    }

    public List<TransactionViewDTO> findTransactionsBySeller(int sellerId) {
        String sql = """
                            SELECT mt.service_id, mt.item_id, mt.negotiated_price, mt.final_price, mt.original_price, mt.is_approved,
                       mi.title AS title, st.first_name AS buyer_name, NULL AS seller_name
                FROM market_transaction mt
                JOIN MarketItem mi ON mt.item_id = mi.item_id
                JOIN Request r ON r.service_id = mt.service_id
                JOIN Student st ON r.student_id = st.student_id
                WHERE mi.user_id = ?

                        """;
        return jdbcTemplate.query(sql, this::mapRowToDTO, sellerId);
    }

    public List<TransactionViewDTO> findTransactionsByBuyer(int buyerId) {
        String sql = """
                            SELECT mt.service_id, mt.item_id, mt.negotiated_price, mt.final_price, mt.original_price, mt.is_approved,
                       mi.title AS title, NULL AS buyer_name, u.first_name AS seller_name
                FROM market_transaction mt
                JOIN MarketItem mi ON mt.item_id = mi.item_id
                JOIN Student u ON mi.user_id = u.student_id
                JOIN Request r ON r.service_id = mt.service_id
                WHERE r.student_id = ?

                        """;
        return jdbcTemplate.query(sql, this::mapRowToDTO, buyerId);
    }

    // public List<MarketTransaction> findTransactionsBySeller(int sellerId) {
    // String sql = """
    // SELECT mt.service_id, mt.item_id, mt.negotiated_price, mt.final_price,
    // mt.original_price, mt.is_approved,
    // mi.title AS title, s.name AS buyerName
    // FROM market_transaction mt
    // JOIN MarketItem mi ON mt.item_id = mi.item_id
    // JOIN Request r ON r.service_id = mt.service_id
    // JOIN Student s ON r.student_id = s.student_id
    // WHERE mi.user_id = ?
    // """;
    // return jdbcTemplate.query(sql, this::mapRow, sellerId);
    // }

    // public List<MarketTransaction> findTransactionsByBuyer(int buyerId) {
    // String sql = """
    // SELECT mt.service_id, mt.item_id, mt.negotiated_price, mt.final_price,
    // mt.original_price, mt.is_approved,
    // mi.title AS title, u.name AS sellerName
    // FROM market_transaction mt
    // JOIN MarketItem mi ON mt.item_id = mi.item_id
    // JOIN Student u ON mi.user_id = u.student_id
    // JOIN Request r ON r.service_id = mt.service_id
    // WHERE r.student_id = ?
    // """;
    // return jdbcTemplate.query(sql, this::mapRow, buyerId);
    // }

    // private MarketTransaction mapRow(ResultSet rs, int rowNum) throws
    // SQLException {
    // MarketTransaction mt = new MarketTransaction();
    // mt.setServiceId(rs.getInt("service_id"));
    // mt.setItemId(rs.getInt("item_id"));
    // mt.setNegotiatedPrice(rs.getBigDecimal("negotiated_price"));
    // mt.setFinalPrice(rs.getBigDecimal("final_price"));
    // mt.setOriginalPrice(rs.getBigDecimal("original_price"));
    // mt.setIsApproved(rs.getBoolean("is_approved"));
    // mt.setTitle(rs.getString("title"));
    // try {
    // mt.setBuyerName(rs.getString("buyerName"));
    // } catch (SQLException ignored) {}
    // try {
    // mt.setSellerName(rs.getString("sellerName"));
    // } catch (SQLException ignored) {}
    // return mt;
    // }

    public void updateFinalPrice(int serviceId, BigDecimal finalPrice) {
        String sql = "UPDATE market_transaction SET final_price = ? WHERE service_id = ?";
        jdbcTemplate.update(sql, finalPrice, serviceId);
    }

    public void approveTransaction(int serviceId, Boolean isApproved) {
        String sql = "UPDATE market_transaction SET is_approved = ? WHERE service_id = ?";
        jdbcTemplate.update(sql, isApproved, serviceId);
    }
}
