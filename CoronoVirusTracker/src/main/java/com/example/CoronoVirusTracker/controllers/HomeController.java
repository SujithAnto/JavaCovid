package com.example.CoronoVirusTracker.controllers;

import com.example.CoronoVirusTracker.Models.LocationStats;
import com.example.CoronoVirusTracker.services.CoronoVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController
{@Autowired
    CoronoVirusDataService coronoVirusDataService;
@GetMapping("/")
    public String home( Model model){
    List<LocationStats> allStats = coronoVirusDataService.getAllStats();
    int totalReportCases=allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
    int totalNewCases=allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
    model.addAttribute("Locations",allStats);
    model.addAttribute("totalReportCases",totalReportCases);
    model.addAttribute("totalNewCases",totalNewCases);
        return "home";
    }
}
