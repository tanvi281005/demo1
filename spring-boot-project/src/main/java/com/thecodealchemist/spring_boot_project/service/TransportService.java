package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.TransportRepository;
import com.thecodealchemist.spring_boot_project.model.TransportBooking;
import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import com.thecodealchemist.spring_boot_project.model.TransportRouteWithTimings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public List<TransportRouteWithTimings> getAvailableBuses(String destination) {
        return transportRepository.findAvailableBuses(destination);
    }

    public int bookTransport(TransportBooking booking) {
        return transportRepository.saveBooking(booking);
    }

    public List<String> fetchuniquedestination() {
        return transportRepository.uniquedestination();
    }
}
