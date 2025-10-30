package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.FoodMenu;
import com.thecodealchemist.spring_boot_project.model.OrderItemDTO;
import com.thecodealchemist.spring_boot_project.model.Cart;
import com.thecodealchemist.spring_boot_project.model.CartItemDTO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FoodRepository {

    private final JdbcTemplate jdbcTemplate;

    public FoodRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clearCart(int studentId) {
        String sql = "UPDATE Cart SET total_price = 0, no_of_items = 0, is_empty = TRUE WHERE student_id = ?";
        jdbcTemplate.update(sql, studentId);
    }

    public BigDecimal getPriceByItemId(int itemId) {
        String sql = "SELECT price FROM FoodMenu WHERE item_id=?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, itemId);
    }

    // ----------------- Food Menu -----------------
    public List<FoodMenu> getAllMenu() {
        String sql = "SELECT * FROM FoodMenu WHERE culture <> 'Water'";
        return jdbcTemplate.query(sql, this::mapRowToFoodMenu);
    }

    public List<FoodMenu> getMenuByCulture(String culture) {
        String sql = "SELECT * FROM FoodMenu WHERE culture=?";
        return jdbcTemplate.query(sql, this::mapRowToFoodMenu, culture);
    }

    private FoodMenu mapRowToFoodMenu(ResultSet rs, int rowNum) throws SQLException {
        FoodMenu menu = new FoodMenu();
        menu.setItemId(rs.getInt("item_id"));
        menu.setVendorId(rs.getInt("vendor_id"));
        menu.setItemName(rs.getString("item_name"));
        menu.setDescription(rs.getString("description"));
        menu.setPrice(rs.getBigDecimal("price"));
        menu.setCulture(rs.getString("culture"));
        menu.setPhoto(rs.getString("photo"));
        return menu;
    }

    public String getCultureByItemId(int itemId) {
        String sql = "SELECT culture FROM FoodMenu WHERE item_id=?";
        return jdbcTemplate.queryForObject(sql, String.class, itemId);
    }

    // ----------------- Orders -----------------
    public int createService(String serviceName) {
        String sql = "INSERT INTO Service (service_name) VALUES (?)";
        jdbcTemplate.update(sql, serviceName);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public void createFoodOrder(int serviceId, int itemId, int quantity, BigDecimal totalPrice, String extras, String instructions) {
        String sql = "INSERT INTO FoodOrder (service_id, item_id, quantity, total_price, extras, instructions) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, serviceId, itemId, quantity, totalPrice, extras, instructions);
    }

    public void createRequest(int studentId, int serviceId) {
        String sql = "INSERT INTO Request (student_id, service_id, status) VALUES (?, ?, 'Pending')";
        jdbcTemplate.update(sql, studentId, serviceId);
    }


    // ----------------- Cart -----------------
    public void upsertCartItem(int studentId, int itemId, int quantity, BigDecimal price, String extras, String instructions, String photo) {
        String checkSql = "SELECT COUNT(*) FROM CartItem WHERE student_id=? AND item_id=?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, studentId, itemId);

        if (count != null && count > 0) {
            if (quantity == 0) {
                jdbcTemplate.update("DELETE FROM CartItem WHERE student_id=? AND item_id=?", studentId, itemId);
            } else {
                String update = "UPDATE CartItem SET quantity=?, price=?, extras=?, instructions=?, photo=? WHERE student_id=? AND item_id=?";
                jdbcTemplate.update(update, quantity, price, extras, instructions,photo, studentId, itemId);
            }
        } else {
            if (quantity > 0) {
                String insert = "INSERT INTO CartItem (student_id, item_id, quantity, price, extras, instructions, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
jdbcTemplate.update(insert, studentId, itemId, quantity, price, extras, instructions, photo);

            }
        }
    }

//     public List<CartItemDTO> getCartItems(int studentId) {
//    String sql = """
//     SELECT
//     c.item_id AS itemId,
//     f.item_name AS itemName,
//     c.price AS price,
//     c.quantity AS quantity,
//     c.extras AS extras,
//     c.instructions AS instructions,
//     c.photo AS photo
// FROM CartItem c
// JOIN FoodMenu f ON c.item_id = f.item_id
// WHERE c.student_id = ?

// """;


//     return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
//         CartItemDTO item = new CartItemDTO();
//         item.setItemId(rs.getInt("item_id"));
//         item.setItemName(rs.getString("item_name"));
//         item.setPrice(rs.getBigDecimal("price"));
//         item.setQuantity(rs.getInt("quantity"));
//         item.setExtras(rs.getString("extras"));
//         item.setInstructions(rs.getString("instructions"));
//         item.setPhoto(rs.getString("photo"));  // ✅ properly maps the photo
//         return item;
//     }, studentId);
// }


    public void updateCartTotals(int studentId) {
        String sql = "SELECT SUM(price*quantity) AS total_price, SUM(quantity) AS no_of_items FROM CartItem WHERE student_id=?";
        jdbcTemplate.query(sql, rs -> {
            BigDecimal total = rs.getBigDecimal("total_price") != null ? rs.getBigDecimal("total_price") : BigDecimal.ZERO;
            int count = rs.getInt("no_of_items");
            String updateCart = "UPDATE Cart SET total_price=?, no_of_items=?, is_empty=? WHERE student_id=?";
            jdbcTemplate.update(updateCart, total, count, count==0, studentId);
        }, studentId);
    }

    // public Cart getCartByStudent(int studentId) {
    //     String sql = "SELECT * FROM Cart WHERE student_id=?";
    //     List<Cart> carts = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
    //         Cart cart = new Cart();
    //         cart.setStudentId(rs.getInt("student_id"));
    //         cart.setTotalPrice(rs.getBigDecimal("total_price"));
    //         cart.setNoOfItems(rs.getInt("no_of_items"));
    //         cart.setCutleryRequired(rs.getBoolean("cutlery_required"));
    //         cart.setItems(getCartItems(studentId));
    //         return cart;
    //     }, studentId);
    //     return carts.isEmpty() ? new Cart() : carts.get(0);
    // }
}
