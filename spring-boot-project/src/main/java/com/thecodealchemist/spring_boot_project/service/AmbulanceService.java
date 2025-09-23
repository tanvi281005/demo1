package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.model.*;
import com.thecodealchemist.spring_boot_project.dao.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmbulanceService {

    private final RequestRepository requestRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final ServiceRepository serviceRepository;
    private final StudentRepository studentRepository;

    public AmbulanceService(RequestRepository requestRepository,
                            AmbulanceRepository ambulanceRepository,
                            ServiceRepository serviceRepository,
                            StudentRepository studentRepository) {
        this.requestRepository = requestRepository;
        this.ambulanceRepository = ambulanceRepository;
        this.serviceRepository = serviceRepository;
        this.studentRepository = studentRepository;
    }
    @Transactional
public void createAmbulanceRequest(Integer studentId,
                                   String fromPos,
                                   String toPos,
                                   String patientCondition,
                                   String ambulanceNo,
                                   Boolean nurseRequired,
                                   String necessity) {

    Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    // Optionally, reuse existing Transport Service
    Services transportService = serviceRepository
        .findByServiceName(Services.ServiceType.TRANSPORT_SERVICES)
        .orElseGet(() -> {
            Services s = new Services();
            s.setServiceName(Services.ServiceType.TRANSPORT_SERVICES);
            serviceRepository.save(s);
            return s;
        });


    // Create request linked to student and service
    Request request = new Request();
    request.setStudent(student);
    request.setService(transportService);
    request.setStatus(Request.Status.Pending);
    requestRepository.save(request);

    // Create ambulance linked to service
    Ambulance ambulance = new Ambulance();
    ambulance.setService(transportService); // PK & FK
    ambulance.setFromPos(fromPos);
    ambulance.setToPos(toPos);
    ambulance.setPatientCondition(patientCondition);
    ambulance.setAmbulanceNo(ambulanceNo);
    ambulance.setNurseRequired(nurseRequired);
    ambulance.setNecessity(necessity);

    ambulanceRepository.save(ambulance);
}


}
