package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.CounsellorRepository;
import com.thecodealchemist.spring_boot_project.model.Counsellor;
import com.thecodealchemist.spring_boot_project.model.CounsellorTimings;
import com.thecodealchemist.spring_boot_project.model.CounsellingSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CounsellorService {

    private final CounsellorRepository counsellorRepository;

    public CounsellorService(CounsellorRepository counsellorRepository) {
        this.counsellorRepository = counsellorRepository;
    }

    public Map<String, Object> getCounsellorDetails(int counsellorId) {
        Counsellor counsellor = counsellorRepository.findById(counsellorId);
        List<CounsellorTimings> timings = counsellorRepository.findAvailableTimings(counsellorId);
        List<CounsellingSession> sessions = counsellorRepository.findSessionsByCounsellor(counsellorId);

        Map<String, Object> result = new HashMap<>();
        result.put("counsellor", counsellor);
        result.put("availableTimings", timings);
        result.put("sessions", sessions);

        return result;
    }
}
