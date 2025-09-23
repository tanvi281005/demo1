package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.model.TransportBooking;
import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import com.thecodealchemist.spring_boot_project.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public List<TransportRouteTiming> getAvailableBuses(String origin, String destination, LocalDate date) {
        return transportRepository.findAvailableBuses(origin, destination, date);
    }

    public int bookTransport(TransportBooking booking) {
        // You can add wallet check logic here
        return transportRepository.saveBooking(booking);
    }
}
