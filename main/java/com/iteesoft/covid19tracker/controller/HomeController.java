package com.iteesoft.covid19tracker.controller;

import com.iteesoft.covid19tracker.model.LocationStats;
import com.iteesoft.covid19tracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
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

    @GetMapping("/country-cases/{country}")
    private String showCasesPerCountry(Model model, @PathVariable(value = "country") String country) throws IOException, InterruptedException {
        List<LocationStats> countryStats = coronaVirusDataService.fetchByCountry(country);
        int totalReportedCase = countryStats.stream()
                .mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCase = countryStats.stream()
                .mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", countryStats);
        model.addAttribute("totalReportedCase", totalReportedCase);
        model.addAttribute("totalNewCase", totalNewCase);
        model.addAttribute("country", totalNewCase);

        return "country_page";
    }
}
