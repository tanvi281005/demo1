package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.CounsellorRepository;
import com.thecodealchemist.spring_boot_project.model.Counsellor;
import com.thecodealchemist.spring_boot_project.model.CounsellorTimings;
import com.thecodealchemist.spring_boot_project.model.CounsellingSession;
import com.thecodealchemist.spring_boot_project.model.Counsellor.Specialization;
import com.thecodealchemist.spring_boot_project.model.CounsellorFormDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.sql.Timestamp;

@Service
public class CounsellorService {

    @Autowired
    private CounsellorRepository counsellorRepository;

    // ✅ Fetch a counsellor’s full profile (details + timings + sessions)
    public Map<String, Object> getCounsellorDetails(int counsellorId) {
        Counsellor counsellor = counsellorRepository.findById(counsellorId);
        List<CounsellorTimings> timings = counsellorRepository.findAvailableTimings(counsellorId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("counsellor", counsellor);
        result.put("availableTimings", timings);

        return result;
    }

    // ✅ Fetch counsellors by specialization (category)
    public List<Counsellor> getCounsellorsByCategory(String category) {
        if (category.equalsIgnoreCase("all")) {
            return counsellorRepository.findAllCounsellors();
        }

        try {
            // Convert category string to enum-friendly format
            Specialization specialization = Specialization.valueOf(
                category.replace(" ", "")
                        .replace("&", "And")
                        .replace("-", "")
            );
            return counsellorRepository.findByCategory(specialization);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid category: " + category);
        }
    }
    public void registerCounsellor(CounsellorFormDTO dto, int studentId) {
    // Convert DTO to model
    Counsellor counsellor = new Counsellor();
    counsellor.setCounsellorId(studentId); // <--- Use logged-in student ID
    counsellor.setSpecialization(Specialization.valueOf(dto.getSpecialization()));
    counsellor.setSelfDescription(dto.getSelfDescription());
    counsellor.setNoOfStudentsCounselled(0);
    counsellor.setRating(0.0);
    counsellor.setJoinedAt(new Timestamp(System.currentTimeMillis()));

    // Save counsellor (ID already set)
    counsellorRepository.saveCounsellorWithId(counsellor);

    // Save timings
    for (String time : dto.getTimings()) {
        CounsellorTimings timing = new CounsellorTimings();
        timing.setCounsellorId(studentId);
        timing.setTiming(java.time.LocalTime.parse(time));
        counsellorRepository.saveTiming(timing);
    }
}


}
