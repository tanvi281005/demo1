package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.CounsellingBookingRepository;
import com.thecodealchemist.spring_boot_project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class CounsellingBookingService {

    @Autowired
    private CounsellingBookingRepository bookingRepository;

    @Autowired
    private StudentNotificationService studentNotificationService;

    // ✅ Book a counselling session
    public void bookCounsellingSession(Student student, Counsellor counsellor, String slot, CounsellingSession.CounsellingMode mode) {
        // 1️⃣ Service
        Services service    = new Services();
        service.setServiceName(Services.ServiceType.MENTORSHIP);
        int serviceId = bookingRepository.insertService(service);
        service.setServiceId(serviceId);

        // 2️⃣ Request
        Request request = new Request();
        request.setStudent(student);
        request.setService(service);
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus(Request.Status.Pending);
        bookingRepository.insertRequest(request);

        // 3️⃣ Counselling session
        CounsellingSession session = new CounsellingSession();
        session.setCounsellorId(counsellor.getCounsellorId());
        session.setServiceId(serviceId);
        session.setIsApproved(false);
        session.setCounsellingMode(mode);
        LocalTime selectedTime = LocalTime.parse(slot);
        session.settimeapproved(Time.valueOf(selectedTime));
        bookingRepository.insertCounsellingSession(session);

        // // 4️⃣ BookingStatus (Pending)
        // bookingRepository.insertBookingStatus(counsellor.getCounsellorId(), serviceId);
        
        // 5️⃣ Notify counsellor
        studentNotificationService.sendNotification(
            counsellor.getCounsellorId(),
            "New counselling request from student " + student.getStudentId() +
            " for " + slot + " (" + mode.name() + ")"
        );
    }

 
 public void updateBookingStatus(Integer serviceId, Integer counsellorId, boolean approved) {
    // Check if this counsellor is linked to this service
    CounsellingSession session = bookingRepository.findCounsellingSession(serviceId, counsellorId);
    if (session == null) {
        throw new RuntimeException("No counselling session found for this counsellor and service.");
    }

    // Update is_approved
    bookingRepository.updateCounsellingApproval(serviceId, counsellorId, approved);

    // Update request status accordingly
    Request.Status newStatus = approved ? Request.Status.Completed : Request.Status.Rejected;
    bookingRepository.updateRequestStatus(serviceId, newStatus);

    // Notify student about the decision
    int studentId = bookingRepository.getStudentIdByServiceId(serviceId);
    String message = approved
            ? "Your counselling request has been approved by counsellor " + counsellorId
            : "Your counselling request was rejected by counsellor " + counsellorId;

    studentNotificationService.sendNotification(studentId, message);
}


}
