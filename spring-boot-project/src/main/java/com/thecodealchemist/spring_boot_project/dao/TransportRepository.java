package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.TransportBooking;
import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TransportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Fetch available buses by destination (only routeId + timings)
    public List<TransportRouteTiming> findAvailableBuses(String destination) {
        String sql = """
            SELECT rt.route_id, t.timings
            FROM TransportRoute rt
            JOIN TransportRouteTimings t ON rt.route_id = t.route_id
            WHERE rt.destination = ?
            ORDER BY t.timings ASC
        """;

        return jdbcTemplate.query(sql, new Object[]{destination}, (rs, rowNum) -> mapRouteTiming(rs));
    }

    private TransportRouteTiming mapRouteTiming(ResultSet rs) throws SQLException {
        int routeId = rs.getInt("route_id");
        String timing = rs.getTime("timings").toLocalTime().toString(); // convert to string
        return new TransportRouteTiming(routeId, timing);
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
