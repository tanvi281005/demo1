package com.thecodealchemist.spring_boot_project.dao;

import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import com.thecodealchemist.spring_boot_project.model.TransportBooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TransportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1️⃣ Get available buses by destination + date
    public List<TransportRouteTiming> findAvailableBuses(String destination) {
        String sql = """
            SELECT rt.route_id, t.timings
            FROM TransportRoute rt
            JOIN TransportRouteTimings t ON rt.route_id = t.route_id
            WHERE rt.destination = ? 
        """;

        return jdbcTemplate.query(sql, new Object[]{destination},
            (rs, rowNum) -> mapRouteTiming(rs));
    }

    private TransportRouteTiming mapRouteTiming(ResultSet rs) throws SQLException {
        int routeId = rs.getInt("route_id");
        LocalTime timing = rs.getTime("timings").toLocalTime();
        return new TransportRouteTiming(routeId, timing);
    }

    // 2️⃣ Save booking
    public int saveBooking(TransportBooking booking) {
        String sql = "INSERT INTO TransportBooking(route_id, wallet_enough, time_chosen, date) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                booking.getRouteId(),
                booking.isWalletEnough(),
                booking.getTimeChosen(),
                booking.getDate());
    }

    public List<String> uniquedestination(){
        String sql= "SELECT DISTINCT destination from TransportRoute ORDER BY destination ASC";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
