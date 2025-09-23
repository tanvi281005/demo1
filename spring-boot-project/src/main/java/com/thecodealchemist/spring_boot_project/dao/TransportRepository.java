package com.thecodealchemist.spring_boot_project.repository;

import com.thecodealchemist.spring_boot_project.model.TransportRoute;
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

    // 1️⃣ Get available routes based on origin, destination, and date
    public List<TransportRouteTiming> findAvailableBuses(String origin, String destination, LocalDate date) {
        String sql = """
            SELECT rt.route_id, t.timing
            FROM TransportRoute rt
            JOIN TransportRouteTimings t ON rt.route_id = t.route_id
            WHERE rt.origin = ? AND rt.destination = ? AND t.date = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{origin, destination, date},
            (rs, rowNum) -> mapRouteTiming(rs, date));
    }

    private TransportRouteTiming mapRouteTiming(ResultSet rs, LocalDate date) throws SQLException {
        int routeId = rs.getInt("route_id");
        LocalTime timing = rs.getTime("timing").toLocalTime();
        return new TransportRouteTiming(routeId, timing, date);
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

    // 3️⃣ Optional: fetch all bookings for a student (if you add studentId column)
    public List<TransportBooking> getBookingsByStudentId(int studentId) {
        String sql = "SELECT service_id, route_id, wallet_enough, time_chosen, date FROM TransportBooking WHERE student_id = ?";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (rs, rowNum) -> {
            return new TransportBooking(
                    rs.getInt("service_id"),
                    rs.getInt("route_id"),
                    rs.getBoolean("wallet_enough"),
                    rs.getTime("time_chosen").toLocalTime(),
                    rs.getDate("date").toLocalDate()
            );
        });
    }
}
