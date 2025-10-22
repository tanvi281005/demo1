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
    double walletBalance = transportRepository.getWalletBalance(studentId);
    double routePrice = transportRepository.getRoutePrice(request.getRouteId());

    if (walletBalance < routePrice) {
        throw new RuntimeException("Not enough money in wallet.");
    }

    // Deduct price and update wallet
    double newBalance = walletBalance - routePrice;
    transportRepository.updateWalletBalance(studentId, newBalance);

    // Continue with normal booking flow
    int serviceId = transportRepository.insertService("Bus Transport");
    transportRepository.insertTransportBooking(serviceId, request.getRouteId(), true, request.getTimeChosen());
    transportRepository.insertRequest(studentId, serviceId);
}

    public List<String> fetchuniquedestination() {
        return transportRepository.uniquedestination();
    }
}
