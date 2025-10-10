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
    SELECT r.route_id, r.origin, r.name, r.destination, t.timings
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

   routeMap.computeIfAbsent(routeId, id ->
            new TransportRouteWithTimings(routeId, origin, name, dest, new ArrayList<>())
        ).getTimings().add(timing); // Add timing to the list
});

return new ArrayList<>(routeMap.values());
    
    }
        
    // Save a booking
    public int saveBooking(TransportBooking booking) {
        String sql = "INSERT INTO TransportBooking(route_id, wallet_enough, time_chosen, date) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                booking.getRouteId(),
                booking.isWalletEnough(),
                booking.getTimeChosen(),
                booking.getDate());
    }

    // Fetch unique destinations
    public List<String> uniquedestination() {
        String sql = "SELECT DISTINCT destination FROM TransportRoute ORDER BY destination ASC";
        List<String> destinations = jdbcTemplate.queryForList(sql, String.class);
        return destinations != null ? destinations : List.of();
    }
}
