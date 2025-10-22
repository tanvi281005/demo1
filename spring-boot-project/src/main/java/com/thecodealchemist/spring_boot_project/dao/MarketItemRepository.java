package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.MarketItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public MarketItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<MarketItem> rowMapper = (rs, rowNum) -> {
        MarketItem item = new MarketItem();
        item.setItemId(rs.getInt("item_id"));
        item.setUserId(rs.getInt("user_id"));
        item.setCategoryName(rs.getString("category_name"));
        item.setTitle(rs.getString("title"));
        item.setPrice(rs.getBigDecimal("price"));
        item.setItemCondition(rs.getString("item_condition"));
        item.setDescription(rs.getString("description"));
        item.setPhoto(rs.getString("photo"));
        item.setAddedAt(rs.getTimestamp("added_at"));
        return item;
    };

    public void save(MarketItem item) {
        String sql = "INSERT INTO MarketItem(user_id, category_name, title, price, item_condition, description, photo, added_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                item.getUserId(),
                item.getCategoryName(),
                item.getTitle(),
                item.getPrice(),
                item.getItemCondition(),
                item.getDescription(),
                item.getPhoto(),
                new Timestamp(System.currentTimeMillis())
        );
    }

    public Optional<MarketItem> findById(Integer itemId) {
        String sql = "SELECT * FROM MarketItem WHERE item_id = ?";
        try {
            MarketItem item = jdbcTemplate.queryForObject(sql, new Object[]{itemId}, rowMapper);
            return Optional.of(item);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<MarketItem> findByCategory(String category) {
        String sql = "SELECT * FROM MarketItem WHERE LOWER(category_name) = LOWER(?)";
        try {
            return jdbcTemplate.query(sql, new Object[]{category}, rowMapper);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<MarketItem> findAll() {
        String sql = "SELECT * FROM MarketItem";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(Integer itemId) {
        String sql = "DELETE FROM MarketItem WHERE item_id = ?";
        jdbcTemplate.update(sql, itemId);
    }

    // ✅ New method: Search items by title (case-insensitive)
    public List<MarketItem> searchByTitle(String keyword) {
        String sql = "SELECT * FROM MarketItem WHERE LOWER(title) LIKE LOWER(?)";
        try {
            return jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%"}, rowMapper);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
