package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.TransportBooking;
import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import com.thecodealchemist.spring_boot_project.service.TransportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;

    // DTO for search request (destination + date)
    public static class CommuteRequest {
        private String destination;

        public String getDestination() {
            return destination;
        }
        public void setDestination(String destination) {
            this.destination = destination;
        }

        
    }

    // 1️⃣ Get available buses (by destination + date)
    @PostMapping("/daily-commute")
    public List<TransportRouteTiming> getDailyCommute(@RequestBody CommuteRequest request) {
        return transportService.getAvailableBuses(request.getDestination());
    }

    // 2️⃣ Book a bus
    @PostMapping("/book")
    public String bookTransport(@RequestBody TransportBooking booking) {
        int rows = transportService.bookTransport(booking);
        return rows > 0 ? "Booking successful" : "Booking failed";
    }

    @GetMapping("/fetchdestination")
    public List<String> getdestination() {
        return transportService.fetchuniquedestination();
    }
    
}
