package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.TransportBooking;
import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import com.thecodealchemist.spring_boot_project.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;

    // 1️⃣ Get available buses
    @PostMapping("/daily-commute")
    public List<TransportRouteTiming> getDailyCommute(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return transportService.getAvailableBuses(origin, destination, date);
    }

    // 2️⃣ Book a bus
    @PostMapping("/book")
    public String bookTransport(@RequestBody TransportBooking booking) {
        int rows = transportService.bookTransport(booking);
        return rows > 0 ? "Booking successful" : "Booking failed";
    }
}
