package com.iteesoft.covid19tracker.controller;

import com.iteesoft.covid19tracker.model.LocationStats;
import com.iteesoft.covid19tracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    private String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCase = allStats.stream()
                .mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCase = allStats.stream()
                .mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCase", totalReportedCase);
        model.addAttribute("totalNewCase", totalNewCase);

        return "home";
    }
}
