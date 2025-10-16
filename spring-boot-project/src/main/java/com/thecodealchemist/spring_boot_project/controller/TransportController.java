package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.BookRequest;
import com.thecodealchemist.spring_boot_project.model.TransportBooking;
import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import com.thecodealchemist.spring_boot_project.service.TransportService;
import com.thecodealchemist.spring_boot_project.service.StudentService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.thecodealchemist.spring_boot_project.model.TransportRouteWithTimings;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transport")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @Autowired
    private StudentService studentService;

    public static class CommuteRequest {
        private String destination;
        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }
    }
    

    @GetMapping("/fetchdestination")
public ResponseEntity<List<String>> getDestinations(HttpSession session) {
    System.out.println("/fetchdestination called; session id: " + session.getId()
                       + ", studentId: " + session.getAttribute("studentId"));
    List<String> destinations = transportService.fetchuniquedestination();
    return ResponseEntity.ok(destinations != null ? destinations : List.of());
}




    @GetMapping("/daily-commute")
public ResponseEntity<?> getDailyCommute(@RequestParam("destination") String destination,
                                         HttpSession session) {
    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return ResponseEntity.status(401).body("You must be logged in.");
    }
    List<TransportRouteWithTimings> buses = transportService.getAvailableBuses(destination);
    System.out.println(buses);
    return ResponseEntity.ok(buses);
}





    // // Book a bus (POST, only for logged-in students)
    // @PostMapping("/book")
    // public ResponseEntity<?> bookTransport(@RequestBody TransportBooking booking,
    //                                        HttpSession session) {
    //     Integer studentId = (Integer) session.getAttribute("studentId");
    //     if (studentId == null) {
    //         return ResponseEntity.status(401).body("You must be logged in to book a bus.");
    //     }

    //     int rows = transportService.bookTransport(booking);
    //     if (rows > 0) {
    //         return ResponseEntity.ok("Booking successful");
    //     } else {
    //         return ResponseEntity.status(500).body("Booking failed");
    //     }
    // }

    @PostMapping("/book")
    public ResponseEntity<String> bookTransport(@RequestBody BookRequest request,
                                                @SessionAttribute(value = "studentId", required = false) Integer studentId) {
        try {
            if (studentId == null) {
                return ResponseEntity.status(401).body("Unauthorized — Please log in first.");
            }

            transportService.bookTransport(studentId, request);
            return ResponseEntity.ok("Booking successful.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error during booking: " + e.getMessage());
        }
    }
}
