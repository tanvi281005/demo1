package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.CounsellingBookingRepository;
import com.thecodealchemist.spring_boot_project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class CounsellingBookingService {

    @Autowired
    private CounsellingBookingRepository bookingRepository;

    public void bookCounsellingSession(Student student, Counsellor counsellor, String slot, CounsellingSession.CounsellingMode mode) {
        // Step 1️⃣: Create new service entry
        Services service = new Services();
        service.setServiceName(Services.ServiceType.MENTORSHIP);
        int serviceId = bookingRepository.insertService(service);
        service.setServiceId(serviceId);

        // Step 2️⃣: Create new request entry
        Request request = new Request();
        request.setStudent(student);
        request.setService(service);
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus(Request.Status.Pending);
        bookingRepository.insertRequest(request);

        // Step 3️⃣: Create counselling session entry
        CounsellingSession session = new CounsellingSession();
        session.setCounsellorId(counsellor.getCounsellorId());
        session.setServiceId(serviceId);
        session.setIsApproved(false);
        session.setCounsellingMode(mode);

        // Convert selected slot (e.g., "15:30") to SQL Time
        LocalTime selectedTime = LocalTime.parse(slot);
        session.settimeapproved(Time.valueOf(selectedTime));

        bookingRepository.insertCounsellingSession(session);
    }
}
