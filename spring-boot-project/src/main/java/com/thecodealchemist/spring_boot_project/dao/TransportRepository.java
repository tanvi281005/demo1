package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.TransportBooking;
import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import com.thecodealchemist.spring_boot_project.model.TransportRouteWithTimings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;
@Repository
public class TransportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Fetch available buses by destination (only routeId + timings)
    public List<TransportRouteWithTimings> findAvailableBuses(String destination) {
        String sql = """
    SELECT r.route_id, r.origin, r.name, r.destination, r.price, t.timings
    FROM transportroute r
    JOIN transportroutetimings t ON r.route_id = t.route_id
    WHERE r.destination = ?
    ORDER BY r.route_id, t.timings
""";

Map<Integer, TransportRouteWithTimings> routeMap = new LinkedHashMap<>();

jdbcTemplate.query(sql, new Object[]{destination}, rs -> {
    int routeId = rs.getInt("route_id");
    String origin = rs.getString("origin");
    String name = rs.getString("name");
    String dest = rs.getString("destination");
    String timing = rs.getTime("timings").toLocalTime().toString();
    int price= rs.getInt("price");

   routeMap.computeIfAbsent(routeId, id ->
            new TransportRouteWithTimings(routeId, origin, name, dest, price, new ArrayList<>())
        ).getTimings().add(timing); // Add timing to the list
});

return new ArrayList<>(routeMap.values());
    
    }
        
    // Save a booking
    public int saveBooking(TransportBooking booking) {
        String sql = "INSERT INTO TransportBooking(route_id, wallet_enough, time_chosen) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                booking.getRouteId(),
                booking.isWalletEnough(),
                booking.getTimeChosen());
    }

    // Fetch unique destinations
    public List<String> uniquedestination() {
        String sql = "SELECT DISTINCT destination FROM TransportRoute ORDER BY destination ASC";
        List<String> destinations = jdbcTemplate.queryForList(sql, String.class);
        return destinations != null ? destinations : List.of();
    }

    public int insertService(String serviceName) {
        String sql = "INSERT INTO service (service_name) VALUES (?)";
        jdbcTemplate.update(sql, serviceName);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    /** Insert into transportbooking */
    public void insertTransportBooking(int serviceId, int routeId, boolean walletEnough, String timeChosen) {
        String sql = """
            INSERT INTO transportbooking (service_id, route_id, wallet_enough, time_chosen)
            VALUES (?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql, serviceId, routeId, walletEnough ? 1 : 0, timeChosen);
    }

    /** Insert into request */
    public void insertRequest(int studentId, int serviceId) {
        String sql = """
            INSERT INTO request (student_id, service_id, created_at, status)
            VALUES (?, ?, NOW(), 'Pending')
        """;
        jdbcTemplate.update(sql, studentId, serviceId);
    }
    // Fetch wallet balance for student
public double getWalletBalance(int studentId) {
    String sql = "SELECT wallet FROM student WHERE student_id = ?";
    Double balance = jdbcTemplate.queryForObject(sql, Double.class, studentId);
    return balance != null ? balance : 0.0;
}

// Fetch price for a route
public double getRoutePrice(int routeId) {
    String sql = "SELECT price FROM transportroute WHERE route_id = ?";
    Double price = jdbcTemplate.queryForObject(sql, Double.class, routeId);
    return price != null ? price : 0.0;
}

// Deduct price from wallet
public void updateWalletBalance(int studentId, double newBalance) {
    String sql = "UPDATE student SET wallet = ? WHERE student_id = ?";
    jdbcTemplate.update(sql, newBalance, studentId);
}

}
