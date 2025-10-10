package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.service.AmbulanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/urgent")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AmbulanceController {

    private final AmbulanceService ambulanceService;

    public AmbulanceController(AmbulanceService ambulanceService) {
        this.ambulanceService = ambulanceService;
    }

    public static class AmbulanceRequestDTO {
        public String fromPos;
        public String toPos;
        public String patientCondition;
        public String ambulanceNo;
        public Boolean nurseRequired;
        public String necessity;
    }

    @PostMapping
    public ResponseEntity<String> urgentRequest(@RequestBody AmbulanceRequestDTO requestDTO,
                                                HttpSession session) {

        // debug log: show incoming session id and stored studentId attribute
        System.out.println("[URGENT] incoming sessionId=" + session.getId() +
                " studentIdAttr=" + session.getAttribute("studentId"));

        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
            return ResponseEntity.status(401).body("Unauthorized: Login required");
        }

        ambulanceService.createAmbulanceRequest(
                studentId,
                requestDTO.fromPos,
                requestDTO.toPos,
                requestDTO.patientCondition,
                requestDTO.ambulanceNo,
                requestDTO.nurseRequired,
                requestDTO.necessity
        );

        return ResponseEntity.ok("Ambulance request submitted successfully!");
    }
}
