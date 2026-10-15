package com.example.first;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    private final AssessmentService assessmentService;
    private final HistoryRepository historyRepository;

    // Constructor injection for both service and repository beans
    public ProjectController(AssessmentService assessmentService, HistoryRepository historyRepository) {
        this.assessmentService = assessmentService;
        this.historyRepository = historyRepository;
    }

    // 1. Serves the Student Skill Profile Input Form Page
    @GetMapping("/")
    public String showProfilePage(Model model) {
        // Crucial Fix: Binding an empty StudentProfile object to the template object name 'profile'
        model.addAttribute("profile", new StudentProfile());
        return "index"; 
    }

    // 2. Processes the form submission and triggers the assessment rule engine
    @PostMapping("/analyze")
    public String analyzeGap(@ModelAttribute("profile") StudentProfile profile, Model model) {
        // Execute the deterministic rule engine calculations
        Map<String, Object> assessmentResult = assessmentService.runAssessment(profile);
        
        // Passing computed results onto the Thymeleaf view layer
        model.addAttribute("studentName", profile.getStudentName());
        model.addAttribute("targetRole", profile.getTargetRole());
        model.addAttribute("score", assessmentResult.get("score"));
        model.addAttribute("missingSkills", assessmentResult.get("missingSkills"));
        model.addAttribute("recommendations", assessmentResult.get("recommendations"));
        
        return "results"; 
    }

    // 3. Displays the consolidated student dashboard with past assessment tables
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<AssessmentHistory> historyList = historyRepository.findAll();
        model.addAttribute("totalAssessments", historyList.size());
        model.addAttribute("historyList", historyList);
        return "dashboard"; 
    }
}
