package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.service.CounsellorService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/counsellors")
public class CounsellorController {

    private final CounsellorService counsellorService;

    public CounsellorController(CounsellorService counsellorService) {
        this.counsellorService = counsellorService;
    }

    // ✅ Fetch counsellor profile + timings + sessions
    @GetMapping("/{counsellorId}")
    public Map<String, Object> getCounsellorDetails(@PathVariable int counsellorId) {
        return counsellorService.getCounsellorDetails(counsellorId);
    }
}
