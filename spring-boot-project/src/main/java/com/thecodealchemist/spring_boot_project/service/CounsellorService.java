package com.thecodealchemist.spring_boot_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.thecodealchemist.spring_boot_project.dao.CounsellorRepository;
import com.thecodealchemist.spring_boot_project.model.Counsellor;
import com.thecodealchemist.spring_boot_project.model.Counsellor.Specialization;

@Service
public class CounsellorService {
    @Autowired
    private CounsellorRepository counsellorRepository;

    public List<Counsellor> getCounsellorsByCategory(String category) {
        if (category.equalsIgnoreCase("all")) {
            return counsellorRepository.findAllCounsellors();
        }

        try {
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
}
