package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.TransportRepository;
import com.thecodealchemist.spring_boot_project.model.BookRequest;
import com.thecodealchemist.spring_boot_project.model.TransportBooking;
import com.thecodealchemist.spring_boot_project.model.TransportRouteTiming;
import com.thecodealchemist.spring_boot_project.model.TransportRouteWithTimings;

import jakarta.transaction.Transactional;

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

    @Transactional
    public void bookTransport(int studentId, BookRequest request) {
        // 1️⃣ Insert service record
        int serviceId = transportRepository.insertService("Bus Transport");

        // 2️⃣ Insert into transportbooking
        transportRepository.insertTransportBooking(serviceId, request.getRouteId(), true, request.getTimeChosen());

        // 3️⃣ Insert into request
        transportRepository.insertRequest(studentId, serviceId);
    }
    public List<String> fetchuniquedestination() {
        return transportRepository.uniquedestination();
    }
}
