package com.example.first;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AssessmentService {

    private final HistoryRepository historyRepository;
    
    // Catalog to store roles and their respective required skills
    private final Map<String, List<String>> roleCatalog = new HashMap<>();
    
    // Map to store skill importance weights
    private final Map<String, Integer> skillWeights = new HashMap<>();

    // Constructor injection for repository and pre-loading catalog data
    public AssessmentService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
        
        // 1. Predefined Role & Skill Catalogue Setup
        roleCatalog.put("Student Skill Gap Analyzer Developer", Arrays.asList("Java 17", "Spring Boot", "MySQL", "Thymeleaf", "Bootstrap"));
        
        // 2. Setting up Importance Weights (High = 3, Medium = 2, Low = 1)
        skillWeights.put("Java 17", 3);
        skillWeights.put("Spring Boot", 3);
        skillWeights.put("MySQL", 2);
        skillWeights.put("Thymeleaf", 1);
        skillWeights.put("Bootstrap", 1);
    }

    // Getter for the role catalog to allow admin modifications via Controller
    public Map<String, List<String>> getRoleCatalog() {
        return this.roleCatalog;
    }

    public Map<String, Object> runAssessment(StudentProfile profile) {
        String targetRole = profile.getTargetRole();
        
        // Fetch predefined skills for the chosen target role
        List<String> requiredSkills = roleCatalog.getOrDefault(targetRole, new ArrayList<>());
        List<String> studentSkills = profile.getCurrentSkills();
        
        List<String> missingSkills = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        
        int totalWeightPoints = 0;
        int earnedWeightPoints = 0;

        // Structured Rule-Based Weighted Comparison Loop
        for (String skill : requiredSkills) {
            int weight = skillWeights.getOrDefault(skill, 1); // Defaults to 1 if weight is undefined
            totalWeightPoints += weight;

            if (studentSkills != null && studentSkills.contains(skill)) {
                earnedWeightPoints += weight; // Student possesses the skill
            } else {
                missingSkills.add(skill); // Skill gap discovered
                recommendations.add("Learn " + skill + " (Weight: " + weight + ") - Highly recommended.");
            }
        }

        // Deterministic Weighted Percentage Score Calculation
        double matchScore = totalWeightPoints == 0 ? 0 : ((double) earnedWeightPoints / totalWeightPoints) * 100;
        int finalScore = (int) Math.round(matchScore);

        // --- Store Assessment History into Relational Database via JPA ---
        AssessmentHistory history = new AssessmentHistory();
        history.setStudentName(profile.getStudentName());
        history.setTargetRole(targetRole);
        history.setFinalScore(finalScore);
        history.setMissingSkillsList(missingSkills.isEmpty() ? "None" : String.join(", ", missingSkills));
        history.setAssessedAt(LocalDateTime.now());
        
        historyRepository.save(history); // Persisting data to MySQL

        // Package results to pass onto the view template
        Map<String, Object> results = new HashMap<>();
        results.put("score", finalScore);
        results.put("missingSkills", missingSkills);
        results.put("recommendations", recommendations);
        
        return results;
    }
}
